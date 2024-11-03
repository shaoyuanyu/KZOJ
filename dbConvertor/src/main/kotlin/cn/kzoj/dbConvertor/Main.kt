package cn.kzoj.dbConvertor

import cn.kzoj.dbConvertor.hojData.configureHojDatabase
import cn.kzoj.dbConvertor.hojData.problem.ProblemEntity
import cn.kzoj.dbConvertor.hojData.problemcase.ProblemCaseEntity
import cn.kzoj.dbConvertor.hojData.problemcase.ProblemCaseTable
import cn.kzoj.dbConvertor.hojData.user.UserInfoEntity
import cn.kzoj.dto.problem.ProblemStatus
import cn.kzoj.dto.user.UserAuthority
import cn.kzoj.persistence.database.configureDatabase
import cn.kzoj.persistence.database.user.UserEntity
import cn.kzoj.persistence.minio.configureMinIO
import cn.kzoj.persistence.minio.problemcase.uploadProblemCaseObject
import io.minio.MinioClient
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.io.File

internal val LOGGER = LoggerFactory.getLogger("cn.kzoj.dbConvertor")

/**
 * 需要设置以下环境变量
 *
 * HOJ_URL / HOJ_USER / HOJ_PASSWORD
 *
 * KZOJ_URL / KZOJ_USER / KZOJ_PASSWORD
 *
 * MINIO_URL / MINIO_USER / MINIO_PASSWORD
 *
 * HOJ_PROBLEM_CASE_PATH
 */
fun main() {
    println("test")

    // hoj 数据库
    val fromDatabase = configureHojDatabase(
        url = System.getenv("HOJ_URL"),
        driver = "com.mysql.cj.jdbc.Driver",
        user = System.getenv("HOJ_USER"),
        password = System.getenv("HOJ_PASSWORD")
    )

    // kzoj 数据库
    val toDatabase = configureDatabase(
        url = System.getenv("KZOJ_URL"),
        driver = "com.mysql.cj.jdbc.Driver",
        user = System.getenv("KZOJ_USER"),
        password = System.getenv("KZOJ_PASSWORD")
    )

    val minioClient = configureMinIO(
        url = System.getenv("MINIO_URL"),
        user = System.getenv("MINIO_USER"),
        password = System.getenv("MINIO_PASSWORD")
    )

    val hojProblemCaseFilePath = System.getenv("HOJ_PROBLEM_CASE_PATH")

    convertProblem(fromDatabase, toDatabase, minioClient, hojProblemCaseFilePath)
    convertUser(fromDatabase, toDatabase)
}


// problem & problem case
fun convertProblem(
    fromDatabase: Database,
    toDatabase: Database,
    minioClient: MinioClient,
    hojProblemCaseFilePath: String
) {
    transaction(fromDatabase) {
        ProblemEntity.all().forEach { hojProblem ->
            if (hojProblem.title == null) {
                return@forEach
            }

            // Problem 表项迁移
            val problemId = transaction(toDatabase) {
                // TODO: 格式化，删除字符串中多余空格等
                cn.kzoj.persistence.database.problem.ProblemEntity.new {
                    title = hojProblem.title.toString()
                    author = hojProblem.author.toString()

                    //
                    createdByUser = ""

                    description = hojProblem.problemDescription.toString()
                    timeLimit = hojProblem.timeLimit
                    memoryLimit = hojProblem.memoryLimit
                    stackLimit = hojProblem.stackLimit
                    inputDescription = hojProblem.inputDescription.toString()
                    outputDescription = hojProblem.outputDescription.toString()
                    examples = hojProblem.examples.toString()
                    problemSource = hojProblem.problemSource.toString()
                    difficulty = hojProblem.difficulty
                    tip = hojProblem.hint.toString()

                    //
                    status = ProblemStatus.PUBLIC

                    score = hojProblem.oiScore
                    utcCreated = Clock.System.now()
                    utcUpdated = this.utcCreated
                }.id.value
            }

            // Problem Case 表项及文件迁移
            ProblemCaseEntity.find {
                ProblemCaseTable.pid eq hojProblem.id.value
            }.forEach { hojProblemCase ->
                val caseIn = "${hojProblemCaseFilePath}/problem_${hojProblemCase.pid}/${hojProblemCase.input}"
                val caseOut = "${hojProblemCaseFilePath}/problem_${hojProblemCase.pid}/${hojProblemCase.output}"

                if (!File(caseIn).exists() || !File(caseOut).exists()) {
                    LOGGER.info("problem_id: ${hojProblemCase.pid}, testcase: ${hojProblemCase.input}, ${hojProblemCase.output} does not exist.")
                    return@forEach
                }

                // Problem Case 表项迁移到 KZOJ 数据库
                transaction(toDatabase) {
                    cn.kzoj.persistence.database.problemcase.ProblemCaseEntity.new {
                        this.problemId = problemId // 新生成的id
                        caseInFile = hojProblemCase.input
                        caseOutFile = hojProblemCase.output
                        score = hojProblemCase.score
                    }
                }

                // Problem Case 文件迁移到 MinIO
                uploadProblemCaseObject(minioClient, "${problemId}/${hojProblemCase.input}", caseIn)
                uploadProblemCaseObject(minioClient, "${problemId}/${hojProblemCase.output}", caseOut)
            }
        }
    }
}


// user
@OptIn(FormatStringsInDatetimeFormats::class)
fun convertUser(fromDatabase: Database, toDatabase: Database) {
    val formatPattern = "yyyy-MM-dd' 'HH:mm:ss[.SSS]" // 从 hoj 读取 datetime 的格式化模板

    transaction(fromDatabase) {
        UserInfoEntity.all().forEach { hojUserInfo ->
            transaction(toDatabase) {
                // TODO: 迁移后提醒学生检查个人信息
                UserEntity.new {
                    username = hojUserInfo.username

                    // hoj 采用 md5 对密码进行单向哈希
                    // 添加 "/MD5/" 标记，以便新数据库兼容 HOJ 密码
                    encryptedPassword = "/MD5/${hojUserInfo.password}"

                    school = hojUserInfo.school.toString()
                    grade = 0

                    realName = hojUserInfo.realname.toString()
                    gender = hojUserInfo.gender.toString()
                    githubHomepage = hojUserInfo.github.toString()
                    email = hojUserInfo.email.toString()
                    avatarHashIndex = "default" // TODO: 默认头像

                    //
                    authority = UserAuthority.USER

                    utcCreated = LocalDateTime.parse(
                        input = hojUserInfo.gmtCreated,
                        format = LocalDateTime.Format { byUnicodePattern(formatPattern) }
                    ).toInstant(TimeZone.UTC)
                    utcUpdated = Clock.System.now()
                }
            }
        }
    }
}
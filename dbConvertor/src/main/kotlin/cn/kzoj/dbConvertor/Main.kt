package cn.kzoj.dbConvertor

import cn.kzoj.dbConvertor.hojData.configureHojDatabase
import cn.kzoj.dbConvertor.hojData.problem.ProblemEntity
import cn.kzoj.dbConvertor.hojData.user.UserInfoEntity
import cn.kzoj.dto.problem.ProblemStatus
import cn.kzoj.dto.user.UserAuthority
import cn.kzoj.persistence.database.configureDatabase
import cn.kzoj.persistence.database.user.UserEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * 需要设置以下环境变量
 *
 * HOJ_URL / HOJ_USER / HOJ_PASSWORD
 *
 * KZOJ_URL / KZOJ_USER / KZOJ_PASSWORD
 */
fun main() {
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

    convertProblem(fromDatabase, toDatabase)
    convertUser(fromDatabase, toDatabase)
}


// problem & problem case
fun convertProblem(fromDatabase: Database, toDatabase: Database) {
    transaction(fromDatabase) {
        ProblemEntity.all().forEach { hojProblem ->

            if (hojProblem.title == null) {
                return@forEach
            }

            // TODO: 格式化，删除字符串中多余空格等
            transaction(toDatabase) {
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
                }
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
                UserEntity.new {
                    username = hojUserInfo.username
                    encryptedPassword = hojUserInfo.password
                    school = hojUserInfo.school.toString()

                    // TODO: 迁移后提醒学生填写grade
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
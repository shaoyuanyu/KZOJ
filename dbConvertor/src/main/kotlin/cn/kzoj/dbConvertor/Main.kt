package cn.kzoj.dbConvertor

import cn.kzoj.dbConvertor.hojData.configureHojDatabase
import cn.kzoj.dbConvertor.hojData.problem.ProblemEntity
import cn.kzoj.dto.problem.ProblemStatus
import cn.kzoj.persistence.database.configureDatabase
import kotlinx.datetime.Clock
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

    // problem & problem case
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
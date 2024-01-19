package cn.kzoj.data.problem

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Problems : Table<Problem>("t_problem") {
    val id = int("id").primaryKey().bindTo { it.id }
    val problemId = varchar("problemId").bindTo { it.problemId }
    val title = varchar("title").bindTo { it.title }
    val author = varchar("author").bindTo { it.author }
    val type = int("type").bindTo { it.type }
    val judgeMode = varchar("judgeMode").bindTo { it.judgeMode }
    val judgeCaseMode = varchar("judgeCaseMode").bindTo { it.judgeCaseMode }
    val timeLimit = int("timeLimit").bindTo { it.timeLimit }
    val memoryLimit = int("memoryLimit").bindTo { it.memoryLimit }
    val stackLimit = int("stackLimit").bindTo { it.stackLimit }
    val description = varchar("description").bindTo { it.description }
}
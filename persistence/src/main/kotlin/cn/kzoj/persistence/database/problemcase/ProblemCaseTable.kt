package cn.kzoj.persistence.database.problemcase

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object ProblemCaseTable: IntIdTable("problem_case") {

    val problemId: Column<Int> = integer("problem_id").index()

    val caseInFile: Column<String> = varchar("case_in_file", 20)

    val caseOutFile: Column<String> = varchar("case_out_file", 20)

    val score: Column<Int> = integer("score")
}
package cn.kzoj.core.problemserver

import cn.kzoj.data.problem.JudgeResult
import cn.kzoj.data.problem.Problem
import cn.kzoj.data.problem.ProblemDAO
import cn.kzoj.data.problem.problemExample
import io.ktor.server.plugins.*
import org.ktorm.database.Database

class ProblemServer(private val database: Database) {
    fun giveProblem(): Problem =
        problemExample

    fun giveProblem(problemId: String): Problem =
        ProblemDAO(database).getProblemByProblemId(problemId)
            ?: throw NotFoundException("Problem with id $problemId not found.")

    fun judgeProblem(): JudgeResult {
        return JudgeResult(accept = true, evaluationPoint = arrayListOf(true, true, true))
    }
}
package cn.kzoj.core.problemserver

import cn.kzoj.core.judge.Judge
import cn.kzoj.data.problem.*
import cn.kzoj.models.SubmitRequest
import cn.kzoj.models.JudgeResult
import cn.kzoj.models.SubmitReceipt
import io.ktor.server.plugins.*
import org.ktorm.database.Database

class ProblemServer(private val database: Database) {
    private val judge = Judge()

    fun giveProblemExample(): Problem =
        problemExample

    fun giveProblem(problemId: String): Problem =
        ProblemDAO(database).getProblemByProblemId(problemId)
            ?: throw NotFoundException("Problem with id $problemId not found.")

    fun judgeProblem(submitRequest: SubmitRequest): SubmitReceipt =
        judge.addJudgeRequest(submitRequest)

    fun queryJudgeStatus(judgeId: String): SubmitReceipt =
        judge.queryJudgeStatus(judgeId)

    fun queryJudgeResult(judgeId: String): JudgeResult =
        judge.queryJudgeResult(judgeId)
}
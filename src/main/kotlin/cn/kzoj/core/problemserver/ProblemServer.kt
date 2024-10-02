package cn.kzoj.core.problemserver

import cn.kzoj.core.judge.Judge
import cn.kzoj.data.problem.*
import cn.kzoj.models.submit.SubmitRequest
import cn.kzoj.models.judge.JudgeResult
import cn.kzoj.models.submit.SubmitReceipt
import io.ktor.server.plugins.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.ktorm.database.Database

class ProblemServer(
    private val database: Database,
    goJudgeUrl: String
) {
    private val judge = Judge(goJudgeUrl)

    @Suppress("unused")
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

    @OptIn(DelicateCoroutinesApi::class)
    @Suppress("unused")
    fun doJudgeTest() {
        GlobalScope.launch {
            judge.doJudgeTest()
        }
    }
}
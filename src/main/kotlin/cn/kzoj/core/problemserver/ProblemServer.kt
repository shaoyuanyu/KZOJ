package cn.kzoj.core.problemserver

import cn.kzoj.core.judge.Judge
import cn.kzoj.data.problem.ProblemDAO
import cn.kzoj.data.problem.ProblemExposed
import cn.kzoj.data.problem.toProblemExposed
import cn.kzoj.data.problem.*
import cn.kzoj.models.submit.SubmitRequest
import cn.kzoj.models.judge.JudgeResult
import cn.kzoj.models.submit.SubmitReceipt
import io.ktor.server.plugins.*
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ProblemServer(
    private val database: Database,
    goJudgeUrl: String
) {
    private val judge = Judge(goJudgeUrl)

    @Suppress("unused")
    fun giveProblemExample(): ProblemExposed =
        problemExposedExample

    suspend fun giveProblem(problemId: String): ProblemExposed =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.find { Problems.problemId eq problemId }.let {
                if (it.empty()) {
                    throw NotFoundException("Problem with id $problemId not found.")
                } else {
                    it.first().toProblemExposed()
                }
            }
        }

    suspend fun createProblemTest() {
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.new {
                problemId = "T001"
                title = "this is title"
                author = "yusy"
                createdByUser = "yusy"
                description = "this is description"
                timeLimit = 1000
                memoryLimit = 1000
                stackLimit = 1000
                inputDescription = "this is input description"
                outputDescription = "this is output description"
                examples = "<input></input><output></output>"
                problemSource = "nowhere"
                difficulty = 0
                tip = "this is tip"
                status = "public"
                score = 100
                localTimeCreated = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                localTimeLastModified = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            }
        }
    }

    fun judgeProblem(submitRequest: SubmitRequest): SubmitReceipt =
        judge.addJudgeRequest(submitRequest)

    fun queryJudgeStatus(judgeId: String): SubmitReceipt =
        judge.queryJudgeStatus(judgeId)

    fun queryJudgeResult(judgeId: String): JudgeResult =
        judge.queryJudgeResult(judgeId)
}
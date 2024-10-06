package cn.kzoj.core.problemserver

import cn.kzoj.core.judge.Judge
import cn.kzoj.data.problem.ProblemDAO
import cn.kzoj.data.problem.*
import cn.kzoj.models.submit.SubmitRequest
import cn.kzoj.models.judge.JudgeResult
import cn.kzoj.models.problem.Problem
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

    suspend fun createProblem(newProblem: Problem): Int =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.new {
                title = newProblem.title
                author = newProblem.author
                createdByUser = newProblem.createdByUser
                description = newProblem.description
                timeLimit = newProblem.timeLimit
                memoryLimit = newProblem.memoryLimit
                stackLimit = newProblem.stackLimit
                inputDescription = newProblem.inputDescription
                outputDescription = newProblem.outputDescription
                examples = newProblem.examples
                problemSource = newProblem.problemSource
                difficulty = newProblem.difficulty
                tip = newProblem.tip
                status = newProblem.status
                score = newProblem.score
                localTimeCreated = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                localTimeLastModified = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            }
        }.id.value

    suspend fun deleteProblem(id: Int?) {
        if (id == null) {
            throw NotFoundException("Problem id should be Int.")
        }

        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.find { Problems.id eq id }.let {
                if (it.empty()) {
                    throw NotFoundException("Problem with id $id not found.")
                } else {
                    it.first().delete()
                }
            }
        }
    }

    suspend fun queryProblemById(id: Int?): Problem =
        if (id == null) {
            throw NotFoundException("Problem id should be Int.")
        } else {
            newSuspendedTransaction(context=Dispatchers.Default, db=database) {
                ProblemDAO.find { Problems.id eq id }.let {
                    if (it.empty()) {
                        throw NotFoundException("Problem with id $id not found.")
                    } else {
                        it.first()
                    }
                }
            }.expose()
        }

    fun judgeProblem(submitRequest: SubmitRequest): SubmitReceipt =
        judge.addJudgeRequest(submitRequest)

    fun queryJudgeStatus(judgeId: String): SubmitReceipt =
        judge.queryJudgeStatus(judgeId)

    fun queryJudgeResult(judgeId: String): JudgeResult =
        judge.queryJudgeResult(judgeId)
}
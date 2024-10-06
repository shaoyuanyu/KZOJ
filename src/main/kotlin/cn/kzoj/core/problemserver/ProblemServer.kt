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
    goJudgeUrl: String,
    testCasePath: String,
    private val database: Database,
) {
    private val judge = Judge(goJudgeUrl, testCasePath)

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
                localTimeLastModified = this.localTimeCreated
            }
        }.id.value

    suspend fun deleteProblem(id: Int?) {
        if (id == null) {
            throw BadRequestException("Problem id should be Int.")
        }

        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.findById(id).let {
                if (it == null) {
                    throw NotFoundException("Problem with id $id not found.")
                }

                it.delete()
            }
        }
    }

    @Suppress("DuplicatedCode")
    suspend fun updateProblem(newProblem: Problem) {
        // TODO: 可能存在篡改id破坏数据库的行为
        if (newProblem.id == null) {
            throw BadRequestException("Problem id should be Int.")
        }

        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.findByIdAndUpdate(newProblem.id) {
                it.title = newProblem.title
                it.author = newProblem.author
                it.description = newProblem.description
                it.timeLimit = newProblem.timeLimit
                it.memoryLimit = newProblem.memoryLimit
                it.stackLimit = newProblem.stackLimit
                it.inputDescription = newProblem.inputDescription
                it.outputDescription = newProblem.outputDescription
                it.examples = newProblem.examples
                it.problemSource = newProblem.problemSource
                it.difficulty = newProblem.difficulty
                it.tip = newProblem.tip
                it.status = newProblem.status
                it.score = newProblem.score
                it.localTimeLastModified = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            }.let {
                if (it == null) {
                    throw NotFoundException("Problem with id ${newProblem.id} not found.")
                }
            }
        }
    }

    suspend fun queryProblemById(id: Int?): Problem =
        if (id == null) {
            throw BadRequestException("Problem id should be Int.")
        } else {
            newSuspendedTransaction(context=Dispatchers.Default, db=database) {
                ProblemDAO.findById(id).let {
                    if (it == null) {
                        throw NotFoundException("Problem with id $id not found.")
                    }

                    it.expose()
                }
            }
        }

    suspend fun queryProblemByTitle(title: String): List<Problem> =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.find { Problems.title like "%$title%" }.also {
                if (it.empty()) {
                    throw NotFoundException("Problem with title containing \"$title\" not found.")
                }
            }.toList().let {
                val problemArrayList: ArrayList<Problem> = arrayListOf()
                it.forEach {
                    problemArrayList.add(it.expose())
                }
                problemArrayList.toList()
            }
        }

    fun judgeProblem(submitRequest: SubmitRequest): SubmitReceipt =
        judge.addJudgeRequest(submitRequest)

    fun queryJudgeStatus(judgeId: String): SubmitReceipt =
        judge.queryJudgeStatus(judgeId)

    fun queryJudgeResult(judgeId: String): JudgeResult =
        judge.queryJudgeResult(judgeId)
}
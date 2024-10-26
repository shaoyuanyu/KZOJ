package cn.kzoj.data.problem

import cn.kzoj.exception.problem.ProblemIdNotFoundException
import cn.kzoj.exception.problem.ProblemIdNotIntException
import cn.kzoj.exception.problem.ProblemPageIndexOutOfRangeException
import cn.kzoj.exception.problem.ProblemTitleNotFoundException
import cn.kzoj.models.problem.Problem
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ProblemService(
    private val database: Database,
) {
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
                utcCreated = Clock.System.now()
                utcLastModified = this.utcCreated
            }
        }.id.value

    suspend fun deleteProblem(id: Int) =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.findById(id).let {
                if (it == null) {
                    throw ProblemIdNotFoundException()
                }

                it.delete()
            }
        }

    @Suppress("DuplicatedCode")
    suspend fun updateProblem(newProblem: Problem) {
        // TODO: 可能存在篡改id破坏数据库的行为
        if (newProblem.id == null) {
            throw ProblemIdNotIntException()
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
                it.utcLastModified = Clock.System.now()
            }.let {
                if (it == null) {
                    throw ProblemIdNotFoundException()
                }
            }
        }
    }

    suspend fun queryProblemById(id: Int): Problem =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.findById(id).let {
                if (it == null) {
                    throw ProblemIdNotFoundException()
                }

                it.expose()
            }
        }

    suspend fun queryProblemByTitle(title: String): List<Problem> =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.find { ProblemTable.title like "%$title%" }.also {
                if (it.empty()) {
                    throw ProblemTitleNotFoundException()
                }
            }.toList().expose()
        }

    // TODO: 增加orderedBy参数及对应功能
    suspend fun queryProblemByPage(pageIndex: Int, pageSize: Int = 20, isAscending: Boolean = true): List<Problem> =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemDAO.all().run {
                if (isAscending) {
                    sortedBy { it.id }
                } else {
                    sortedByDescending { it.id }
                }
            }.let {
                it.subList(
                    fromIndex = with ((pageIndex-1) * pageSize) {
                        if (this > it.size - 1) {
                            throw ProblemPageIndexOutOfRangeException()
                        } else {
                            this
                        }
                    },
                    toIndex = with (pageIndex * pageSize) {
                        if (this > it.size) {
                            it.size
                        } else {
                            this
                        }
                    },
                )
            }.toList().expose()
        }
}
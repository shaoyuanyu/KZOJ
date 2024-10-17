package cn.kzoj.data.problemcase

import cn.kzoj.models.problemcase.ProblemCase
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ProblemCaseService(
    private val database: Database,
) {
    suspend fun getProblemCaseList(problemId: Int): List<ProblemCase> =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemCaseDAO.find { ProblemCaseTable.problemId eq problemId }.toList().let {
                val problemCaseArrayList: ArrayList<ProblemCase> = arrayListOf()
                it.forEach { problemCaseArrayList.add(it.expose()) }
                problemCaseArrayList.toList()
            }
        }
}
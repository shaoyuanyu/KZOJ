package cn.kzoj.persistence

import cn.kzoj.dto.problemcase.ProblemCase
import cn.kzoj.persistence.database.problemcase.ProblemCaseEntity
import cn.kzoj.persistence.database.problemcase.ProblemCaseTable
import cn.kzoj.persistence.database.problemcase.expose
import cn.kzoj.persistence.minio.problemcase.getProblemCaseObject
import io.minio.MinioClient
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ProblemCaseService(
    private val database: Database,
    private val minioClient: MinioClient,
) {
    suspend fun getProblemCaseList(problemId: Int): List<ProblemCase> =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemCaseEntity.find { ProblemCaseTable.problemId eq problemId }.toList().let {
                val problemCaseArrayList: ArrayList<ProblemCase> = arrayListOf()
                it.forEach { problemCaseArrayList.add(it.expose()) }
                problemCaseArrayList.toList()
            }
        }

    fun getProblemCaseContent(path: String, caseInFilename: String, caseOutFilename:String): Pair<String, String> =
        getProblemCaseObject(minioClient, "$path/$caseInFilename").run {
            val caseIn = readAllBytes().toString(Charsets.UTF_8)
            close()
            caseIn
        } to getProblemCaseObject(minioClient, "$path/$caseOutFilename").run {
            val caseOut = readAllBytes().toString(Charsets.UTF_8)
            close()
            caseOut
        }
}
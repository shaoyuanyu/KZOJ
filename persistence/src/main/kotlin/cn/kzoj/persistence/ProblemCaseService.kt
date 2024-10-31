package cn.kzoj.persistence

import cn.kzoj.persistence.minio.MinioBucketConfig
import cn.kzoj.api.dto.problemcase.ProblemCase
import cn.kzoj.persistence.database.problemcase.ProblemCaseDAO
import cn.kzoj.persistence.database.problemcase.ProblemCaseTable
import cn.kzoj.persistence.database.problemcase.expose
import io.minio.GetObjectArgs
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
            ProblemCaseDAO.find { ProblemCaseTable.problemId eq problemId }.toList().let {
                val problemCaseArrayList: ArrayList<ProblemCase> = arrayListOf()
                it.forEach { problemCaseArrayList.add(it.expose()) }
                problemCaseArrayList.toList()
            }
        }

    fun getProblemCaseContent(path: String, caseInFilename: String, caseOutFilename:String): Pair<String, String> =
        minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(MinioBucketConfig.BucketNames.PROBLEM_CASES)
                .`object`("$path/$caseInFilename")
                .build()
        ).run {
            val caseIn = readAllBytes().toString(Charsets.UTF_8)
            close()
            caseIn
        } to minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(MinioBucketConfig.BucketNames.PROBLEM_CASES)
                .`object`("$path/$caseOutFilename")
                .build()
        ).run {
            val caseOut = readAllBytes().toString(Charsets.UTF_8)
            close()
            caseOut
        }
}
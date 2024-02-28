package cn.kzoj.core.problemserver

import cn.kzoj.core.judge.Judge
import cn.kzoj.data.problem.*
import io.ktor.server.plugins.*
import org.ktorm.database.Database

class ProblemServer(private val database: Database) {
    private val judge = Judge()

    fun giveProblem(): Problem =
        problemExample

    fun giveProblem(problemId: String): Problem =
        ProblemDAO(database).getProblemByProblemId(problemId)
            ?: throw NotFoundException("Problem with id $problemId not found.")

    fun judgeProblem(judgeRequest: JudgeRequest): String =
        judge.addJudgeRequest(judgeRequest)

    fun queryJudgeResult(judgeId: String): JudgeResult =
        judge.queryJudgeResult(judgeId)
}
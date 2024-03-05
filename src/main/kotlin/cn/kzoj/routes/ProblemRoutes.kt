package cn.kzoj.routes

import cn.kzoj.core.problemserver.ProblemServer
import cn.kzoj.models.submit.SubmitRequest
import cn.kzoj.data.problem.toProblemDetail
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.problemRoutes(problemServer: ProblemServer) {
    routing {
        route("/problem") {
            getProblem(problemServer)
            submitProblem(problemServer)
            queryJudgeStatus(problemServer)
            queryJudgeResult(problemServer)
        }
    }
}

/**
 * 获取题目
 *
 * 接收: String
 *
 * 返回: ProblemDetail
 */
fun Route.getProblem(problemServer: ProblemServer) {
    get("/get/{id}") {
        val id = this.context.parameters["id"].toString()
        call.respond(
            problemServer.giveProblem(id).toProblemDetail()
        )
    }
}

/**
 * 提交
 *
 * 接收: SubmitRequest
 *
 * 返回: SubmitReceipt
 */
fun Route.submitProblem(problemServer: ProblemServer) {
    post("/submit") {
        val submitRequest = call.receive<SubmitRequest>()

        call.respond(
            problemServer.judgeProblem(submitRequest)
        )
    }
}

/**
 * 查询判题状态
 *
 * 接收: String
 *
 * 返回: SubmitReceipt
 */
fun Route.queryJudgeStatus(problemServer: ProblemServer) {
    get("/judgeStatus") {
        val judgeId = call.receiveText()

        call.respond(
            problemServer.queryJudgeStatus(judgeId)
        )
    }
}

/**
 * 查询判题结果
 *
 * 接收: String
 *
 * 返回: JudgeResult
 */
fun Route.queryJudgeResult(problemServer: ProblemServer) {
    get("/judgeResult") {
        val judgeId = call.receiveText()

        call.respond(
            problemServer.queryJudgeResult(judgeId)
        )
    }
}
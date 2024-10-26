package cn.kzoj.routes

import cn.kzoj.core.JudgeDispatcher
import cn.kzoj.models.submit.SubmitRequest
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.judgeRoutes(judgeDispatcher: JudgeDispatcher) {
    routing {
        // TODO: 暂时沿用"/problem"路径，待API基本稳定再统一修改
        route("/problem") {
            submitProblem(judgeDispatcher)
            queryJudgeStatus(judgeDispatcher)
            queryJudgeResult(judgeDispatcher)
        }
    }
}

/**
 * 提交
 *
 * 接收提交请求: SubmitRequest
 *
 * 返回提交收据: SubmitReceipt
 */
fun Route.submitProblem(judgeDispatcher: JudgeDispatcher) {
    post("/submit") {
        val submitRequest = call.receive<SubmitRequest>()

        call.respond(
            judgeDispatcher.addJudgeRequest(submitRequest)
        )
    }
}

/**
 * 查询判题状态
 *
 * 接收judgeId: String
 *
 * 返回提交收据: SubmitReceipt
 */
fun Route.queryJudgeStatus(judgeDispatcher: JudgeDispatcher) {
    get("/judgeStatus/{judgeId}") {
        val judgeId = call.parameters["judgeId"].toString()

        call.respond(
            judgeDispatcher.queryJudgeStatus(judgeId)
        )
    }
}

/**
 * 查询判题结果
 *
 * 接收JudgeId: String
 *
 * 返回判题结果: JudgeResult
 */
fun Route.queryJudgeResult(judgeDispatcher: JudgeDispatcher) {
    get("/judgeResult/{judgeId}") {
        val judgeId = call.parameters["judgeId"].toString()

        call.respond(
            judgeDispatcher.queryJudgeResult(judgeId)
        )
    }
}
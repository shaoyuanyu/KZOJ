package cn.kzoj.routes

import cn.kzoj.core.problemserver.ProblemServer
import cn.kzoj.models.problem.Problem
import cn.kzoj.models.submit.SubmitRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.problemRoutes(problemServer: ProblemServer) {
    routing {
        route("/problem") {
            // CRUD
            createProblem(problemServer)
            deleteProblem(problemServer)
            queryProblemById(problemServer)

            // 判题
            submitProblem(problemServer)
            queryJudgeStatus(problemServer)
            queryJudgeResult(problemServer)
        }
    }
}

/**
 * 创建题目
 *
 * 接收题目：Problem
 *
 * 返回id：Int
 */
fun Route.createProblem(problemServer: ProblemServer) {
    post("/create") {
        val newProblem = call.receive<Problem>()

        call.respond(
            problemServer.createProblem(newProblem)
        )
    }
}

/**
 * 删除题目
 *
 * 接收id：Int
 */
fun Route.deleteProblem(problemServer: ProblemServer) {
    delete("/{id}") {
        val id = this.context.parameters["id"].toString().toIntOrNull()

        problemServer.deleteProblem(id)

        // TODO: 返回
    }
}

/**
 * 根据id查询题目
 *
 * 接收id: Int
 *
 * 返回题目: Problem
 */
fun Route.queryProblemById(problemServer: ProblemServer) {
    get("/get/{id}") {
        val id = this.context.parameters["id"].toString().toIntOrNull()

        call.respond(
            problemServer.queryProblemById(id)
        )
    }
}


/**
 * 提交
 *
 * 接收提交请求: SubmitRequest
 *
 * 返回提交收据: SubmitReceipt
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
 * 接收judgeId: String
 *
 * 返回提交收据: SubmitReceipt
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
 * 接收JudgeId: String
 *
 * 返回判题结果: JudgeResult
 */
fun Route.queryJudgeResult(problemServer: ProblemServer) {
    get("/judgeResult") {
        val judgeId = call.receiveText()

        call.respond(
            problemServer.queryJudgeResult(judgeId)
        )
    }
}
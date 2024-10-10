package cn.kzoj.routes

import cn.kzoj.core.problemserver.ProblemServer
import cn.kzoj.models.problem.Problem
import cn.kzoj.models.submit.SubmitRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.problemRoutes(problemServer: ProblemServer) {
    routing {
        route("/problem") {
            // CRUD
            createProblem(problemServer)
            deleteProblem(problemServer)
            updateProblem(problemServer)
            queryProblemById(problemServer)
            queryProblemByTitle(problemServer)
            queryProblemByPage(problemServer)

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

        problemServer.createProblem(newProblem)

        call.response.status(HttpStatusCode.Created)
    }
}

/**
 * 删除题目
 *
 * 接收id：Int
 */
fun Route.deleteProblem(problemServer: ProblemServer) {
    delete("/{id}") {
        val id = call.parameters["id"].toString().toIntOrNull()

        problemServer.deleteProblem(id)

        call.response.status(HttpStatusCode.OK)
    }
}

/**
 * 更新题目
 *
 * 接收题目: Problem
 */
fun Route.updateProblem(problemServer: ProblemServer) {
    put("/update") {
        val newProblem = call.receive<Problem>()

        problemServer.updateProblem(newProblem)

        call.response.status(HttpStatusCode.OK)
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
        val id = call.parameters["id"].toString().toIntOrNull()

        call.respond(
            problemServer.queryProblemById(id)
        )
    }
}

/**
 * 根据标题查询题目
 *
 * 接收标题（关键字）：String
 *
 * 返回题目列表：List<Problem>
 */
fun Route.queryProblemByTitle(problemServer: ProblemServer) {
    get("/queryByTitle/{title}") {
        val title = call.parameters["title"].toString()

        call.respond(
            problemServer.queryProblemByTitle(title)
        )
    }
}

/**
 * 按页查询题目
 *
 * QueryParameters:
 * pageIndex: Int           （当前页数，从1开始）
 * pageSize?: Int           （页大小，默认为20）
 * isAscending?: Boolean    （是否升序，默认为true）
 *
 * 返回题目列表：List<Problem>
 */
fun Route.queryProblemByPage(problemServer: ProblemServer) {
    get("/queryByPage") {
        val pageIndex = call.request.queryParameters["pageIndex"].toString().toIntOrNull()
        val pageSize = call.request.queryParameters["pageSize"].toString().toIntOrNull()
        val isAscending = when (call.request.queryParameters["isAscending"].toString()) {
            "false" -> false
            else -> true
        }

        call.respond(
            if (pageIndex == null) {
                throw BadRequestException("page index should be Int.")
            } else if (pageIndex <= 0) {
                throw BadRequestException("page index should be positive Int.")
            } else if (pageSize == null || pageSize <= 0) {
                problemServer.queryProblemByPage(pageIndex = pageIndex, isAscending = isAscending)
            } else {
                problemServer.queryProblemByPage(pageIndex = pageIndex, pageSize = pageSize, isAscending = isAscending)
            }
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
    get("/judgeStatus/{judgeId}") {
        val judgeId = call.parameters["judgeId"].toString()

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
    get("/judgeResult/{judgeId}") {
        val judgeId = call.parameters["judgeId"].toString()

        call.respond(
            problemServer.queryJudgeResult(judgeId)
        )
    }
}
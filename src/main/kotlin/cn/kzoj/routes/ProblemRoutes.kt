package cn.kzoj.routes

import cn.kzoj.data.problem.ProblemService
import cn.kzoj.exception.problem.ProblemIdNotIntException
import cn.kzoj.exception.problem.ProblemPageIndexNotPositiveIntException
import cn.kzoj.exception.user.UserAuthorityException
import cn.kzoj.models.problem.Problem
import cn.kzoj.models.user.UserAuthority
import cn.kzoj.models.user.UserSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions

fun Application.problemRoutes(problemService: ProblemService) {
    routing {
        route("/problem") {
            // CRUD
            authenticate("auth-session-user") {
                createProblem(problemService)
                deleteProblem(problemService)
                updateProblem(problemService)
            }
            queryProblemById(problemService)
            queryProblemByTitle(problemService)
            queryProblemByPage(problemService)
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
fun Route.createProblem(problemService: ProblemService) {
    post("/create") {
        val newProblem = call.receive<Problem>()

        problemService.createProblem(newProblem)

        call.response.status(HttpStatusCode.Created)
    }
}

/**
 * 删除题目
 *
 * 接收id：Int
 */
fun Route.deleteProblem(problemService: ProblemService) {
    delete("/{id}") {
        val id = call.parameters["id"].toString().toIntOrNull()
        if (id == null) {
            throw ProblemIdNotIntException()
        }

        // 权限校验，仅管理员或创建者可操作
        val userSession = call.sessions.get<UserSession>()
        if (userSession == null) {
            throw UserAuthorityException()
        }
        if (userSession.userAuthority != UserAuthority.ADMIN &&
            problemService.queryProblemById(id).createdByUser != userSession.userId
        ) {
            throw UserAuthorityException()
        }

        problemService.deleteProblem(id)

        call.response.status(HttpStatusCode.OK)
    }
}

/**
 * 更新题目
 *
 * 接收题目: Problem
 */
// TODO: Problem的更新也要通过Session实现控制，前端先访问query以显示题目编辑页面，此时后端设置Session，编辑完成后调用update，后端对Session进行校验，防止用户篡改题目id
fun Route.updateProblem(problemService: ProblemService) {
    put("/update") {
        val newProblem = call.receive<Problem>()
        // TODO: 可能存在篡改id破坏数据库的行为
        if (newProblem.id == null) {
            throw ProblemIdNotIntException()
        }

        // 权限校验，仅管理员或创建者可操作
        val userSession = call.sessions.get<UserSession>()
        if (userSession == null) {
            throw UserAuthorityException()
        }
        if (userSession.userAuthority != UserAuthority.ADMIN &&
            problemService.queryProblemById(newProblem.id).createdByUser != userSession.userId
        ) {
            throw UserAuthorityException()
        }

        problemService.updateProblem(newProblem)

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
fun Route.queryProblemById(problemService: ProblemService) {
    get("/get/{id}") {
        val id = call.parameters["id"].toString().toIntOrNull()

        if (id == null) {
            throw ProblemIdNotIntException()
        }

        call.respond(
            problemService.queryProblemById(id)
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
fun Route.queryProblemByTitle(problemService: ProblemService) {
    get("/queryByTitle/{title}") {
        val title = call.parameters["title"].toString()

        call.respond(
            problemService.queryProblemByTitle(title)
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
fun Route.queryProblemByPage(problemService: ProblemService) {
    get("/queryByPage") {
        val pageIndex = call.request.queryParameters["pageIndex"].toString().toIntOrNull()
        val pageSize = call.request.queryParameters["pageSize"].toString().toIntOrNull()
        val isAscending = when (call.request.queryParameters["isAscending"].toString()) {
            "false" -> false
            else -> true
        }

        call.respond(
            if (pageIndex == null || pageIndex <= 0) {
                throw ProblemPageIndexNotPositiveIntException()
            } else if (pageSize == null || pageSize <= 0) {
                problemService.queryProblemByPage(pageIndex = pageIndex, isAscending = isAscending)
            } else {
                problemService.queryProblemByPage(pageIndex = pageIndex, pageSize = pageSize, isAscending = isAscending)
            }
        )
    }
}

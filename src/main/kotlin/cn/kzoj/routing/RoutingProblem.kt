package cn.kzoj.routing

import cn.kzoj.data.DatabaseProvider
import cn.kzoj.data.problem.ProblemDAO
import cn.kzoj.data.problem.toProblemDetail
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.problemRoutes() {
    routing {
        route(RoutePath.PROBLEM) {
            getProblem()
        }
    }
}

fun Route.getProblem() {
    get("/{id}") {
        val id = this.context.parameters["id"].toString()

        call.respond(
            ProblemDAO(DatabaseProvider.database).getProblemByProblemId(id)
                ?.toProblemDetail() ?: "null"
        )
    }
}
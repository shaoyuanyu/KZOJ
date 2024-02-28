package cn.kzoj.routes

import cn.kzoj.core.problemserver.ProblemServer
import cn.kzoj.data.problem.toProblemDetail
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.problemRoutes(problemServer: ProblemServer) {
    routing {
        route("/problem") {
            getProblem(problemServer)
        }
    }
}

fun Route.getProblem(problemServer: ProblemServer) {
    get("/get/{id}") {
        val id = this.context.parameters["id"].toString()
        call.respond(problemServer.giveProblem(id).toProblemDetail())
    }
}
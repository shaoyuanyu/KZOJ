package cn.kzoj.routing

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
        val id = this.context.parameters["id"]
        // test
        call.respondText("problem id: $id")
    }
}
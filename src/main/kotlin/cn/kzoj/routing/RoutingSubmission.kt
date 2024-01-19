package cn.kzoj.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.submissionRoutes() {
    routing {
        route(RoutePath.SUBMISSION) {
        }
    }
}
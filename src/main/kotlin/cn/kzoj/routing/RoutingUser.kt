package cn.kzoj.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.userRoutes() {
    routing {
        route(RoutePath.USER) {
        }
    }
}
package cn.kzoj.routes

import cn.kzoj.core.problemserver.ProblemServer
import io.ktor.server.application.*

fun Application.routesGraph(problemServer: ProblemServer) {
    userRoutes() // "/user"
    problemRoutes(problemServer) // "/problem"
    submissionRoutes() // "/submission"
}
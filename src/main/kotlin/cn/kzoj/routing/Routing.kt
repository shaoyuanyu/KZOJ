package cn.kzoj.routing

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        singlePageApplication {
            // "vue-app" 应当位于项目根目录下，为vue工程的静态编译
            vue("vue-app")
        }
    }

    routesGraph()
}

fun Application.routesGraph() {
    userRoutes() // user
    problemRoutes() // problem
    submissionRoutes() // submission
}

object RoutePath {
    const val USER = "/user"
    const val PROBLEM = "/problem"
    const val SUBMISSION = "/submission"
}
package cn.kzoj.plugins

import cn.kzoj.core.problemserver.ProblemServer
import cn.kzoj.routes.problemRoutes
import cn.kzoj.routes.submissionRoutes
import cn.kzoj.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting(problemServer: ProblemServer) {
    routing {
        singlePageApplication {
            // "vue-app" 应当位于项目根目录下，为vue工程的静态编译
            vue("vue-app")
        }
    }

    userRoutes() // "/user"
    problemRoutes(problemServer) // "/problem"
    submissionRoutes() // "/submission"
}
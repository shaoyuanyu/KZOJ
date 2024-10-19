package cn.kzoj.plugins

import cn.kzoj.core.JudgeDispatcher
import cn.kzoj.data.problem.ProblemService
import cn.kzoj.data.user.UserService
import cn.kzoj.routes.judgeRoutes
import cn.kzoj.routes.problemRoutes
import cn.kzoj.routes.submissionRoutes
import cn.kzoj.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: UserService,
    problemService: ProblemService,
    judgeDispatcher: JudgeDispatcher,
) {
    routing {
        singlePageApplication {
            // "vue-app" 应当位于项目根目录下，为vue工程的静态编译
            vue("vue-app")
        }
    }

    userRoutes(userService)
    problemRoutes(problemService)
    judgeRoutes(judgeDispatcher)
    submissionRoutes()
}
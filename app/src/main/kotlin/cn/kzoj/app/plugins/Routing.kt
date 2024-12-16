package cn.kzoj.app.plugins

import cn.kzoj.app.routes.judgeRoutes
import cn.kzoj.app.routes.problemRoutes
import cn.kzoj.app.routes.submissionRoutes
import cn.kzoj.app.routes.userRoutes
import cn.kzoj.judge.JudgeDispatcher
import cn.kzoj.persistence.ProblemService
import cn.kzoj.persistence.UserService
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
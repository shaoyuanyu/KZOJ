package cn.kzoj.web.plugins

import cn.kzoj.judge.JudgeDispatcher
import cn.kzoj.persistence.ProblemService
import cn.kzoj.persistence.UserService
import cn.kzoj.web.routes.judgeRoutes
import cn.kzoj.web.routes.problemRoutes
import cn.kzoj.web.routes.submissionRoutes
import cn.kzoj.web.routes.userRoutes
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
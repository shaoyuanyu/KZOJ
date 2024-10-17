package cn.kzoj

import cn.kzoj.core.JudgeDispatcher
import cn.kzoj.data.problem.ProblemService
import cn.kzoj.data.problemcase.ProblemCaseService
import cn.kzoj.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureCORS()
    configureSerialization()
    configureStatusPages()
    val database = configureDatabase()
    val minioClient = configureMinIO()

    // 数据服务
    val problemService = ProblemService(database)
    val problemCaseService = ProblemCaseService(database)

    // 判题调度器
    val judgeDispatcher = JudgeDispatcher(
        goJudgeUrl = environment.config.property("gojudge.url").getString(),
        problemCaseService = problemCaseService,
        minioClient = minioClient
    )

    // 路由
    configureRouting(problemService, judgeDispatcher)
}

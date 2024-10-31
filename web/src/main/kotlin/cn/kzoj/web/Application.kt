package cn.kzoj.web

import cn.kzoj.judge.JudgeDispatcher
import cn.kzoj.persistence.ProblemService
import cn.kzoj.persistence.ProblemCaseService
import cn.kzoj.persistence.UserService
import cn.kzoj.persistence.database.configureDatabase
import cn.kzoj.persistence.minio.configureMinIO
import cn.kzoj.web.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val database = configureDatabase(
        url = environment.config.property("database.url").getString(),
        driver = environment.config.property("database.driver").getString(),
        user = environment.config.property("database.user").getString(),
        password = environment.config.property("database.password").getString()
    )
    val minioClient = configureMinIO(
        url = environment.config.property("minio.url").getString(),
        user = environment.config.property("minio.user").getString(),
        password = environment.config.property("minio.password").getString()
    )

    // 数据服务
    val userService = UserService(database, minioClient)
    val problemService = ProblemService(database)
    val problemCaseService = ProblemCaseService(database, minioClient)

    // 判题调度器
    val judgeDispatcher = JudgeDispatcher(
        goJudgeUrl = environment.config.property("gojudge.url").getString(),
        problemCaseService = problemCaseService
    )

    configureSecurity(userService)
    configureCORS()
    configureSerialization()
    configureStatusPages()

    // 路由
    configureRouting(userService, problemService, judgeDispatcher)
}
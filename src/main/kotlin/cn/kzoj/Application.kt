package cn.kzoj

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
    val problemServer = configureProblemServer(database)
    val minioClient = configureMinIO()
    configureRouting(problemServer)
}

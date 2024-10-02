package cn.kzoj

import cn.kzoj.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureCORS()
    configureSecurity()
    configureSerialization()
    val database = configureDatabase()
    val problemServer = configureProblemServer(database)
    configureRouting(problemServer)

//    problemServer.doJudgeTest()
}

package cn.kzoj.plugins

import cn.kzoj.core.problemserver.ProblemServer
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureProblemServer(database: Database): ProblemServer =
    ProblemServer(
        database = database,
        goJudgeUrl = environment.config.property("gojudge.url").getString(),
        testCasePath = environment.config.property("local_data.test_case.path").getString(),
    )
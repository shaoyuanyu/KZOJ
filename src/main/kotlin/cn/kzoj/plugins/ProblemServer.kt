package cn.kzoj.plugins

import cn.kzoj.core.problemserver.ProblemServer
import io.ktor.server.application.*
import io.minio.MinioClient
import org.jetbrains.exposed.sql.Database

fun Application.configureProblemServer(
    database: Database,
    minioClient: MinioClient
) = ProblemServer(
        database = database,
        minioClient = minioClient,
        goJudgeUrl = environment.config.property("gojudge.url").getString(),
    )
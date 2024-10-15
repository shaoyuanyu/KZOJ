package cn.kzoj.plugins

import io.ktor.server.application.Application
import io.minio.MinioClient

fun Application.configureMinIO(): MinioClient {
    val url = environment.config.propertyOrNull("minio.url")?.getString()
    val user = environment.config.propertyOrNull("minio.user")?.getString()
    val password = environment.config.propertyOrNull("minio.password")?.getString()

    if (url.isNullOrEmpty()) {
        throw Exception("MinIO url configuration does not exist or is empty.")
    }
    if (user.isNullOrEmpty()) {
        throw Exception("MinIO user configuration does not exist or is empty.")
    }
    if (password.isNullOrEmpty()) {
        throw Exception("MinIO password configuration does not exist or is empty.")
    }

    val minioClient = MinioClient.builder()
        .endpoint(url)
        .credentials(user, password)
        .build()

    return minioClient
}
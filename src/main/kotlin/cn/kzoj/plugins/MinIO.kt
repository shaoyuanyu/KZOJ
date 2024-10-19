package cn.kzoj.plugins

import cn.kzoj.common.minio.MinioBucketConfig
import io.ktor.server.application.Application
import io.ktor.server.application.log
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient

fun Application.configureMinIO(): MinioClient {
    // 读取参数
    val url = environment.config.propertyOrNull("minio.url")?.getString()
    val user = environment.config.propertyOrNull("minio.user")?.getString()
    val password = environment.config.propertyOrNull("minio.password")?.getString()

    // 检验参数
    if (url.isNullOrEmpty()) {
        throw Exception("MinIO url configuration does not exist or is empty.")
    }
    if (user.isNullOrEmpty()) {
        throw Exception("MinIO user configuration does not exist or is empty.")
    }
    if (password.isNullOrEmpty()) {
        throw Exception("MinIO password configuration does not exist or is empty.")
    }

    // 创建MinIO客户端
    val minioClient = MinioClient.builder()
        .endpoint(url)
        .credentials(user, password)
        .build()

    // 检查bucket
    MinioBucketConfig.BucketNameList.forEach { bucketName ->
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            log.info("MinIO $bucketName bucket doesn't exist and will be created.")

            minioClient.makeBucket(
                MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build()
            )
        }
    }

    return minioClient
}
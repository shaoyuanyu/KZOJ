package cn.kzoj.persistence.minio

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import org.slf4j.LoggerFactory

internal val LOGGER = LoggerFactory.getLogger("cn.kzoj.persistence")

fun configureMinIO(
    url: String,
    user: String,
    password: String,
): MinioClient {
    // 创建MinIO客户端
    val minioClient = MinioClient.builder()
        .endpoint(url)
        .credentials(user, password)
        .build()

    // 检查bucket
    MinioBucketConfig.BucketNameList.forEach { bucketName ->
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            LOGGER.info("MinIO $bucketName bucket doesn't exist and will be created.")

            minioClient.makeBucket(
                MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build()
            )
        }
    }

    return minioClient
}
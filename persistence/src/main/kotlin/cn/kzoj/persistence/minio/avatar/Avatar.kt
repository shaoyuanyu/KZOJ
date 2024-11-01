package cn.kzoj.persistence.minio.avatar

import cn.kzoj.persistence.minio.MinioBucketConfig
import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import java.io.InputStream

fun putAvatarObject(
    minioClient: MinioClient,
    name: String,
    inputStream: InputStream,
) {
    minioClient.putObject(
        PutObjectArgs.builder()
            .bucket(MinioBucketConfig.BucketNames.AVATARS)
            .`object`(name)
            .stream(inputStream, -1, 10485760)
            .contentType("image/jpg")
            .build()
    )
}

fun getAvatarObject(minioClient: MinioClient, name: String): InputStream =
    minioClient.getObject(
        GetObjectArgs.builder()
            .bucket(MinioBucketConfig.BucketNames.AVATARS)
            .`object`(name)
            .build()
    )
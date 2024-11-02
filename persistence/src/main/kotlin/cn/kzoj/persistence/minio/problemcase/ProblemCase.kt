package cn.kzoj.persistence.minio.problemcase

import cn.kzoj.persistence.minio.MinioBucketConfig
import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.UploadObjectArgs
import java.io.InputStream

fun getProblemCaseObject(
    minioClient: MinioClient,
    name: String
) = minioClient.getObject(
        GetObjectArgs.builder()
            .bucket(MinioBucketConfig.BucketNames.PROBLEM_CASES)
            .`object`(name)
            .build()
    )

fun putProblemCaseObject(
    minioClient: MinioClient,
    name: String,
    inputStream: InputStream
) {
    minioClient.putObject(
        PutObjectArgs.builder()
            .bucket(MinioBucketConfig.BucketNames.PROBLEM_CASES)
            .`object`(name)
            .stream(inputStream, -1, 10485760)
            .build()
    )
}

fun uploadProblemCaseObject(
    minioClient: MinioClient,
    name: String,
    file: String
) {
    minioClient.uploadObject(
        UploadObjectArgs.builder()
            .bucket(MinioBucketConfig.BucketNames.PROBLEM_CASES)
            .`object`(name)
            .filename(file)
            .build()
    )
}
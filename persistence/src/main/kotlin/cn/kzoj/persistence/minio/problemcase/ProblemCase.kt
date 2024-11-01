package cn.kzoj.persistence.minio.problemcase

import cn.kzoj.persistence.minio.MinioBucketConfig
import io.minio.GetObjectArgs
import io.minio.MinioClient

fun getProblemCaseObject(
    minioClient: MinioClient,
    name: String
) = minioClient.getObject(
        GetObjectArgs.builder()
            .bucket(MinioBucketConfig.BucketNames.PROBLEM_CASES)
            .`object`(name)
            .build()
    )
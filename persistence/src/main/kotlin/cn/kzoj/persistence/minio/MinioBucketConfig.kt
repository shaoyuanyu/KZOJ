package cn.kzoj.persistence.minio

object MinioBucketConfig {
    object BucketNames {
        const val PROBLEM_CASES = "problem-cases"
        const val AVATARS = "avatars"
    }

    val BucketNameList = listOf<String>(
        BucketNames.PROBLEM_CASES,
        BucketNames.AVATARS
    )
}
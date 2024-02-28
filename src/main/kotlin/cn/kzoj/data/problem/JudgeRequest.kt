package cn.kzoj.data.problem

import java.time.LocalDateTime

data class JudgeRequest(
    val problemId: String,
    val userId: String,
    val judgeId: String? = null,
    val submitTime: LocalDateTime,
    val userAnswer: String,
)
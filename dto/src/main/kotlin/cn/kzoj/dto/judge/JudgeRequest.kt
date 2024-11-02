package cn.kzoj.dto.judge

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class JudgeRequest(
    val submitRequest: SubmitRequest,
    val submitTime: Instant, // UTC
    val judgeId: String,
)

package cn.kzoj.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class JudgeRequest(
    val submitRequest: SubmitRequest,
    val submitTime: Instant,
    val judgeId: String,
)

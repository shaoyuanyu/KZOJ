package cn.kzoj.models.judge

import cn.kzoj.models.submit.SubmitRequest
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class JudgeRequest(
    val submitRequest: SubmitRequest,
    val submitTime: Instant,
    val judgeId: String,
)

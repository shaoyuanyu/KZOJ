package cn.kzoj.dto.judge

import kotlinx.serialization.Serializable

@Serializable
data class SubmitRequest(
    val problemId: Int,
    val userId: String,
    val lang: String,
    val submittedCode: String,
)
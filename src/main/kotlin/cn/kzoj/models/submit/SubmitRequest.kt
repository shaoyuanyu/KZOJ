package cn.kzoj.models.submit

import kotlinx.serialization.Serializable

@Serializable
data class SubmitRequest(
    val problemId: Int,
    val userId: String,
    val lang: String,
    val submittedCode: String,
)
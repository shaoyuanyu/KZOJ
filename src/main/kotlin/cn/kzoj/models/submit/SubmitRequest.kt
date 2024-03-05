package cn.kzoj.models.submit

import kotlinx.serialization.Serializable

@Serializable
data class SubmitRequest(
    val problemId: String,
    val userId: String,
    val lang: String,
    val submittedCode: String,
)
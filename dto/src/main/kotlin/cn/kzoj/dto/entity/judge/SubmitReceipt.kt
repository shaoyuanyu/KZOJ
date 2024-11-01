package cn.kzoj.dto.entity.judge

import kotlinx.serialization.Serializable

@Serializable
data class SubmitReceipt(
    val judgeId: String,
    val status: JudgeStatus,
    val positionInQueue: Int,
)

package cn.kzoj.models

import cn.kzoj.common.JudgeStatus
import kotlinx.serialization.Serializable

@Serializable
data class SubmitReceipt(
    val judgeId: String,
    val status: JudgeStatus,
    val positionInQueue: Int,
)

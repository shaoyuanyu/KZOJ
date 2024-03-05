package cn.kzoj.models.submit

import cn.kzoj.common.JudgeStatus
import kotlinx.serialization.Serializable

@Serializable
data class SubmitReceipt(
    val judgeId: String,
    val status: JudgeStatus,
    val positionInQueue: Int,
)

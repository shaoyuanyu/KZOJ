package cn.kzoj.models

import cn.kzoj.common.JudgeStatus
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class JudgeResult(
    val judgeId: String,
    val status: JudgeStatus,
    val accept: Boolean? = null,
    val evaluationPoint: ArrayList<Boolean>? = null,
    val judgeTime: Instant? = null,
)
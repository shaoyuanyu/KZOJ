package cn.kzoj.dto.entity.judge

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class JudgeResult(
    val judgeId: String,
    val status: JudgeStatus,
    var accept: Boolean? = null,
    var evaluationPoint: ArrayList<Boolean>? = null,
    val judgeTime: Instant, // UTC
)
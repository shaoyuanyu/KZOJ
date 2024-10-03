package cn.kzoj.models.judge

import cn.kzoj.common.JudgeStatus
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class JudgeResult(
    val judgeId: String,
    val status: JudgeStatus,
    var accept: Boolean? = null,
    var evaluationPoint: ArrayList<Boolean>? = null,
    val judgeTime: Instant? = null,
)
package cn.kzoj.data.problem

import java.time.LocalDateTime

data class JudgeResult(
    val judgeId: String,
    val status: JudgeStatus,
    val accept: Boolean? = null,
    val evaluationPoint: ArrayList<Boolean>? = null,
    val judgeTime: LocalDateTime? = null,
)

enum class JudgeStatus {
    QUEUEING,
    FINISHED
}
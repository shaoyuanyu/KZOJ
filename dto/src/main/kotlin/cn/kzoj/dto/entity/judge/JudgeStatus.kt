package cn.kzoj.dto.entity.judge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
enum class JudgeStatus {
    @SerialName("Queueing")     Queueing,
    @SerialName("Judging")      Judging,
    @SerialName("Finished")     Finished,
    @SerialName("NotFound")     NotFound,
}
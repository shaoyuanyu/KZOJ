package cn.kzoj.dto.problem

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ProblemStatus {
    @SerialName("PUBLIC")
    PUBLIC,
    @SerialName("PRIVATE")
    PRIVATE,
    @SerialName("IN_RACE")
    IN_RACE,
}
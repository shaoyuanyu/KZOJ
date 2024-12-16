package cn.kzoj.dto.problemcase

import kotlinx.serialization.Serializable

@Serializable
data class ProblemCase(
    val problemId: Int,
    val caseInFile: String,
    val caseOutFile: String,
    val score: Int,
)

package cn.kzoj.dto.problem

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Problem(
    val id: Int? = null,
    val title: String,
    val author: String,
    val createdByUser: String,
    val description: String,
    val timeLimit: Long,
    val memoryLimit: Long,
    val stackLimit: Long,
    val inputDescription: String,
    val outputDescription: String,
    val examples: String,
    val problemSource: String,
    val difficulty: Int,
    val tip: String,
    val status: String,
    val score: Int,
    val utcCreated: Instant? = null,
    val utcLastModified: Instant? = null,
)
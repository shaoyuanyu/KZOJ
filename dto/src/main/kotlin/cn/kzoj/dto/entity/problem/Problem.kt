package cn.kzoj.dto.entity.problem

import kotlinx.datetime.Clock
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

@Suppress("unused")
val problemExample = Problem(
    id = 1,
    title = "title",
    author = "yusy",
    createdByUser = "yusy",
    description = "description",
    timeLimit = 1000,
    memoryLimit = 128,
    stackLimit = 128,
    inputDescription = "input description",
    outputDescription = "output description",
    examples = "<input></input><output></output>",
    problemSource = "nowhere",
    difficulty = 0,
    tip = "tip",
    status = "public",
    score = 100,
    utcCreated = Clock.System.now(),
    utcLastModified = Clock.System.now(),
)
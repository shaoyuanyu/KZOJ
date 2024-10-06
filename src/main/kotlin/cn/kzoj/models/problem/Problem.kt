package cn.kzoj.models.problem

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
    val localTimeCreated: LocalDateTime? = null,
    val localTimeLastModified: LocalDateTime? = null,
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
    localTimeCreated = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    localTimeLastModified = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
)
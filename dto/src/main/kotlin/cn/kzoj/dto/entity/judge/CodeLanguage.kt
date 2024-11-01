package cn.kzoj.dto.entity.judge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("unused")
@Serializable
enum class CodeLanguage {
    @SerialName("c")        C,
    @SerialName("cpp")      Cpp,
    @SerialName("python")   Python,
}
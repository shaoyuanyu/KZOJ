package cn.kzoj.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserAuthority {
    @SerialName("ADMIN")    ADMIN,
    @SerialName("USER")     USER,
}
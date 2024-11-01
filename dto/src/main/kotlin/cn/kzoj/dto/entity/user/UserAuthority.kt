package cn.kzoj.dto.entity.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserAuthority {
    @SerialName("ADMIN")    ADMIN,
    @SerialName("USER")     USER,
}
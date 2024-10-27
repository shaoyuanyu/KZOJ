package cn.kzoj.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
    val userId: String, // uuid
    val username: String,
    val userAuthority: UserAuthority,
)

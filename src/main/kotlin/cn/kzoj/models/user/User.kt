package cn.kzoj.models.user

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uuid: String? = null,
    val username: String,
    val plainPassword: String? = null, // 仅创建用户或修改密码时使用，其余场景赋null // TODO:前端是否也对密码做加密
    val encryptedPassword: String? = null,
    val school: String,
    val grade: Int,
    val realName: String,
    val gender: String,
    val githubHomepage: String,
    val email: String,
    val avatarHashIndex: String? = null,
    val authority: UserAuthority = UserAuthority.USER,
    val utcCreated: Instant? = null,
    val utcUpdated: Instant? = null,
)
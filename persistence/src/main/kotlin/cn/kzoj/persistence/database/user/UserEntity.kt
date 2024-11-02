package cn.kzoj.persistence.database.user

import cn.kzoj.dto.user.User
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class UserEntity(uuid: EntityID<UUID>) : UUIDEntity(uuid) {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var username by UserTable.username
    var encryptedPassword by UserTable.encryptedPassword
    var school by UserTable.school
    var grade by UserTable.grade
    var realName by UserTable.realName
    var gender by UserTable.gender
    var githubHomepage by UserTable.githubHomepage
    var email by UserTable.email
    var avatarHashIndex by UserTable.avatarHashIndex
    var authority by UserTable.authority
    var utcCreated by UserTable.utcCreated
    var utcUpdated by UserTable.utcUpdated
}

fun UserEntity.expose(): User =
    User(
        uuid = this.id.value.toString(),
        username = this.username,
        encryptedPassword = this.encryptedPassword,
        school = this.school,
        grade = this.grade,
        realName = this.realName,
        gender = this.gender,
        githubHomepage = this.githubHomepage,
        email = this.email,
        authority = this.authority,
        utcCreated = this.utcCreated,
        utcUpdated = this.utcUpdated
    )

fun UserEntity.exposeWithoutPasswd(): User =
    User(
        uuid = this.id.value.toString(),
        username = this.username,
        encryptedPassword = null,
        school = this.school,
        grade = this.grade,
        realName = this.realName,
        gender = this.gender,
        githubHomepage = this.githubHomepage,
        email = this.email,
        authority = this.authority,
        utcCreated = this.utcCreated,
        utcUpdated = this.utcUpdated
    )
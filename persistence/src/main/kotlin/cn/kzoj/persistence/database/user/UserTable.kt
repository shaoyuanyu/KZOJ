package cn.kzoj.persistence.database.user

import cn.kzoj.dto.user.UserAuthority
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object UserTable: UUIDTable("user") {

    /**
     * username 唯一
     */
    val username: Column<String> = varchar("username", 100).uniqueIndex()
    // val username: Column<String> = varchar("username", 100).index()

    val encryptedPassword: Column<String> = text("encrypted_password")

    val school: Column<String> = varchar("school", 100)

    val grade: Column<Int> = integer("grade")

    val realName: Column<String> = varchar("real_name", 100)

    val gender: Column<String> = varchar("gender", 20)

    val githubHomepage: Column<String> = text("github_homepage")

    val email: Column<String> = text("email")

    val avatarHashIndex: Column<String> = varchar("avatar_hash_index", 64)

//    val status = customEnumeration(
//        name = "status",
//        sql = "ENUM('ONLINE', 'OFFLINE')",
//        fromDb = { value -> Status.valueOf(value as String) },
//        toDb = { it.name }
//    )

    val authority = customEnumeration(
        name = "authority",
        sql = "ENUM('ADMIN', 'USER')",
        fromDb = { value -> UserAuthority.valueOf(value as String) },
        toDb = { it.name }
    )

    val utcCreated: Column<Instant> = timestamp("utc_created")

    val utcUpdated: Column<Instant> = timestamp("utc_updated")
}
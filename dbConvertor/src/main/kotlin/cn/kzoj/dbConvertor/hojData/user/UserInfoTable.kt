package cn.kzoj.dbConvertor.hojData.user

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object UserInfoTable: IdTable<String>("user_info") {

    override val id: Column<EntityID<String>> = text("uuid").entityId()

    val username: Column<String> = text("username")

    val password: Column<String> = text("password")

    val nickname: Column<String?> = text("nickname").nullable()

    val school: Column<String?> = text("school").nullable()

    val course: Column<String?> = text("course").nullable()

    val number: Column<String?> = text("number").nullable()

    val realname: Column<String?> = text("realname").nullable()

    val gender: Column<String?> = text("gender").nullable()

    val github: Column<String?> = text("github").nullable()

    val blog: Column<String?> = text("blog").nullable()

    val cfUsername: Column<String?> = text("cf_username").nullable()

    val email: Column<String?> = text("email").nullable()

    val avatar: Column<String?> = text("avatar").nullable()

    val signature: Column<String?> = text("signature").nullable()

    val titleName: Column<String?> = text("title_name").nullable()

    val titleColor: Column<String?> = text("title_color").nullable()

    val status: Column<Int?> = integer("status").nullable()

    val gmtCreated: Column<String> = text("gmt_create")

    val gmtModified: Column<String> = text("gmt_modified")
}
package cn.kzoj.dbConvertor.hojData.user

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column

object UserInfoTable: UUIDTable("user_info") {

    val username: Column<String> = text("username")

    val password: Column<String> = text("password")

    val nickname: Column<String> = text("nickname")

    val school: Column<String> = text("school")

    val course: Column<String> = text("course")

    val number: Column<String> = text("number")

    val realname: Column<String> = text("realname")

    val gender: Column<String> = text("gender")

    val github: Column<String> = text("github")

    val blog: Column<String> = text("blog")

    val cfUsername: Column<String> = text("cf_username")

    val email: Column<String> = text("email")

    val avatar: Column<String> = text("avatar")

    val signature: Column<String> = text("signature")

    val titleName: Column<String> = text("title_name")

    val titleColor: Column<String> = text("title_color")

    val status: Column<Int> = integer("status")

    val gmtCreated: Column<String> = text("gmt_create")

    val gmtModified: Column<String> = text("gmt_modified")
}
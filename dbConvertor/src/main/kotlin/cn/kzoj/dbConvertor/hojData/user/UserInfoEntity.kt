package cn.kzoj.dbConvertor.hojData.user

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

@Suppress("unused")
class UserInfoEntity(uuid: EntityID<String>) : Entity<String>(uuid) {
    companion object : EntityClass<String, UserInfoEntity>(UserInfoTable)

    var username by UserInfoTable.username
    var password by UserInfoTable.password
    var nickname by UserInfoTable.nickname
    var school by UserInfoTable.school
    var course by UserInfoTable.course
    var number by UserInfoTable.number
    var realname by UserInfoTable.realname
    var gender by UserInfoTable.gender
    var github by UserInfoTable.github
    var blog by UserInfoTable.blog
    var cfUsername by UserInfoTable.cfUsername
    var email by UserInfoTable.email
    var avatar by UserInfoTable.avatar
    var signature by UserInfoTable.signature
    var titleName by UserInfoTable.titleName
    var titleColor by UserInfoTable.titleColor
    var status by UserInfoTable.status
    var gmtCreated by UserInfoTable.gmtCreated
    var gmtModified by UserInfoTable.gmtModified
}
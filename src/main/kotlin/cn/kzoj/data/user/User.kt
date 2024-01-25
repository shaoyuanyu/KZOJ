package cn.kzoj.data.user

import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface User: Entity<User> {
    companion object : Entity.Factory<User>()

    /**
     * id (主键)
     */
    val id: Int

    /**
     * 用户名
     */
    var username: String

    /**
     * 实名
     */
    var realName: String

    /**
     * 性别
     */
    var gender: String

    /**
     * 学校
     */
    var school: String?

    /**
     * 毕业年份 (xx届)
     */
    var graduationYear: String

    /**
     * github主页
     */
    var githubPage: String?

    /**
     * email地址
     */
    var emailAddress: String

    /**
     * 头像url
     */
    var avatarUrl: String

    /**
     * 头衔
     */
    var titleName: String

    /**
     * 在线状态
     */
    var onlineStatus: Int

    /**
     * 密码 (加密后)
     */
    var passwd: String

    /**
     * 加密算法
     */
    var encryptFunc: String

    /**
     * 公钥
     */
    var publicKey: String

    /**
     * 私钥
     */
    var privateKey: String

    /**
     * 创建日期
     */
    var dateCreated: LocalDateTime

    /**
     * 最后修改日期
     */
    var dateLastModified: LocalDateTime
}
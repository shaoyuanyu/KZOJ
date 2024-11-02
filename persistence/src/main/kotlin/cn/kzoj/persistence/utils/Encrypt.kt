package cn.kzoj.persistence.utils

import org.mindrot.jbcrypt.BCrypt
import java.math.BigInteger
import java.security.MessageDigest

/**
 * 采用 BCrypt 算法
 */
fun encryptPasswd(plainPassword: String) = BCrypt.hashpw(plainPassword, BCrypt.gensalt())

/**
 * 验证密码，兼容 BCrypt 算法和 MD5 算法
 *
 * 由于老系统（HOJ）使用的是 MD5 算法来加密存储密码，需要兼容，
 * 通过识别数据库中密码项前有无 /MD5/ 标记来区分
 *
 * 在 cn.kzoj.dbConvertor 模块中可见，
 * 迁移 HOJ 数据库时在 password 一栏的所有值前加入了 /MD5/ 标记
 */
fun validatePasswd(plainPassword: String, encryptedPassword: String): Boolean =
    if (encryptedPassword.startsWith("/MD5/")) {
        // 使用 MD5 校验
        BigInteger(1, MessageDigest.getInstance("md5").digest(plainPassword.toByteArray())).toString(16) == encryptedPassword.removeRange(0, 5)
    } else {
        // 使用 BCrypt 校验
        BCrypt.checkpw(plainPassword, encryptedPassword)
    }
package cn.kzoj.persistence

import cn.kzoj.dto.exception.user.UserIdNotFoundException
import cn.kzoj.dto.exception.user.UsernameDuplicatedException
import cn.kzoj.dto.exception.user.UsernameNotFoundException
import cn.kzoj.dto.user.User
import cn.kzoj.dto.user.UserAuthority
import cn.kzoj.persistence.database.user.UserEntity
import cn.kzoj.persistence.database.user.UserTable
import cn.kzoj.persistence.database.user.expose
import cn.kzoj.persistence.database.user.exposeWithoutPasswd
import cn.kzoj.persistence.minio.avatar.getAvatarObject
import cn.kzoj.persistence.minio.avatar.putAvatarObject
import cn.kzoj.persistence.utils.encryptPasswd
import cn.kzoj.persistence.utils.validatePasswd
import io.minio.MinioClient
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.util.UUID

internal val LOGGER = LoggerFactory.getLogger("cn.kzoj.persistence")

class UserService(
    private val database: Database,
    private val minioClient: MinioClient
) {
    init {
        // 若 User 表为空（新创建），则自动创建默认 admin 账户
        // TODO: 提醒管理员修改初始密码
        transaction(database) {
            if (UserEntity.all().empty()) {
                UserEntity.new {
                    username = "admin"
                    encryptedPassword = encryptPasswd("12345")
                    school = ""
                    grade = 100
                    realName = ""
                    gender = ""
                    githubHomepage = ""
                    email = ""
                    avatarHashIndex = "default" // TODO:设置默认头像
                    authority = UserAuthority.ADMIN
                    utcCreated = Clock.System.now()
                    utcUpdated = this.utcCreated
                }
            }
        }
    }

    /**
     * 创建用户
     */
    suspend fun createUser(newUser: User) =
        try {
            newSuspendedTransaction(context = Dispatchers.Default, db = database) {
                UserEntity.new {
                    username = newUser.username
                    encryptedPassword = encryptPasswd(newUser.plainPassword!!)
                    school = newUser.school
                    grade = newUser.grade
                    realName = newUser.realName
                    gender = newUser.gender
                    githubHomepage = newUser.githubHomepage
                    email = newUser.email
                    avatarHashIndex = "default" // TODO:设置默认头像
                    authority = newUser.authority
                    utcCreated = Clock.System.now()
                    utcUpdated = this.utcCreated
                }.id.value.toString()
            }.also { uuid ->
                LOGGER.info("New user is created, uuid: $uuid")
            }
        } catch (_: ExposedSQLException) {
            throw UsernameDuplicatedException()
        }

    /**
     * 根据userId（uuid）查询用户
     */
    suspend fun queryUserByUUID(uuid: String): User =
        newSuspendedTransaction(context = Dispatchers.Default, db = database) {
            UserEntity.findById(UUID.fromString(uuid)).let {
                if (it == null) {
                    throw UserIdNotFoundException()
                }

                it.exposeWithoutPasswd()
            }
        }

    /**
     * 更新用户信息
     */
    @Suppress("DuplicatedCode")
    suspend fun updateUser(newUser: User) {
        try {
            newSuspendedTransaction(context = Dispatchers.Default, db = database) {
                UserEntity.findByIdAndUpdate(UUID.fromString(newUser.uuid)) {
                    it.username = newUser.username
                    it.encryptedPassword = encryptPasswd(newUser.plainPassword!!)
                    it.school = newUser.school
                    it.grade = newUser.grade
                    it.realName = newUser.realName
                    it.gender = newUser.gender
                    it.githubHomepage = newUser.githubHomepage
                    it.email = newUser.email
                    it.authority = newUser.authority
                    it.utcUpdated = Clock.System.now()
                }
            }
        } catch (_: ExposedSQLException) {
            throw UsernameDuplicatedException()
        }
    }

    /**
     * 删除用户
     */
    suspend fun deleteUser(userId: String) {
        newSuspendedTransaction(context = Dispatchers.Default, db = database) {
            UserEntity.findById(UUID.fromString(userId)).let {
                if (it == null) {
                    throw UserIdNotFoundException()
                }

                it.delete()
            }
        }
    }

    /**
     * 登录验证
     */
    suspend fun validate(username: String, password: String): String? =
        newSuspendedTransaction(context = Dispatchers.Default, db = database) {
            UserEntity.find {
                UserTable.username eq username
            }.let {
                if (it.empty()) {
                    throw UsernameNotFoundException()
                }

                val user = it.toList()[0].expose()

                // 验证密码
                if (validatePasswd(password, user.encryptedPassword!!)) {
                    user.uuid.toString()
                } else {
                    null
                }
            }
        }


    /**
     * 上传头像文件
     *
     * userId 为 uuid
     */
    suspend fun uploadAvatar(userId: String, avatarFileInputStream: InputStream) {
        val avatarHashIndex = userId

        putAvatarObject(minioClient, avatarHashIndex, avatarFileInputStream)

        avatarFileInputStream.close()

        newSuspendedTransaction(context = Dispatchers.Default, db = database) {
            UserEntity.findByIdAndUpdate(UUID.fromString(userId)) {
                it.avatarHashIndex = avatarHashIndex
            }
        }
    }

    /**
     * 获取头像文件输入流
     */
    fun getAvatar(userId: String): InputStream = getAvatarObject(minioClient, userId)
}
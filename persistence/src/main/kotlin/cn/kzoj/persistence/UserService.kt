package cn.kzoj.persistence

import cn.kzoj.dto.exception.user.UserIdNotFoundException
import cn.kzoj.dto.exception.user.UsernameDuplicatedException
import cn.kzoj.dto.exception.user.UsernameNotFoundException
import cn.kzoj.dto.entity.user.User
import cn.kzoj.persistence.database.user.UserDAO
import cn.kzoj.persistence.database.user.UserTable
import cn.kzoj.persistence.database.user.expose
import cn.kzoj.persistence.database.user.exposeWithoutPasswd
import cn.kzoj.persistence.minio.avatar.getAvatarObject
import cn.kzoj.persistence.minio.avatar.putAvatarObject
import io.minio.MinioClient
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.io.InputStream
import java.util.UUID

// TODO: LOG
// internal val LOGGER = KtorSimpleLogger("cn.kzoj.data.UserService")

class UserService(
    private val database: Database,
    private val minioClient: MinioClient
) {
    /**
     * 创建用户
     */
    suspend fun createUser(newUser: User) =
        try {
            newSuspendedTransaction(context = Dispatchers.Default, db = database) {
                UserDAO.new {
                    username = newUser.username
                    encryptedPassword = newUser.plainPassword!! // TODO:加密
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
                // TODO: LOG
                // LOGGER.info("New user is created, uuid: $uuid")
            }
        } catch (_: ExposedSQLException) {
            throw UsernameDuplicatedException()
        }

    /**
     * 根据userId（uuid）查询用户
     */
    suspend fun queryUserByUUID(uuid: String): User =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            UserDAO.findById(UUID.fromString(uuid)).let {
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
            newSuspendedTransaction(context=Dispatchers.Default, db=database) {
                UserDAO.findByIdAndUpdate(UUID.fromString(newUser.uuid)) {
                    it.username = newUser.username
                    it.encryptedPassword = newUser.plainPassword!! // TODO:加密
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
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            UserDAO.findById(UUID.fromString(userId)).let {
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
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            UserDAO.find {
                UserTable.username eq username
            }.let {
                if (it.empty()) {
                    throw UsernameNotFoundException()
                }

                // TODO: 解密
                val user = it.toList()[0].expose()
                if (user.encryptedPassword == password) {
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

        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            UserDAO.findByIdAndUpdate(UUID.fromString(userId)) {
                it.avatarHashIndex = avatarHashIndex
            }
        }
    }

    /**
     * 获取头像文件输入流
     */
    fun getAvatar(userId: String): InputStream = getAvatarObject(minioClient, userId)
}
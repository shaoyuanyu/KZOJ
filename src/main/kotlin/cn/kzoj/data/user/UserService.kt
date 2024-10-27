package cn.kzoj.data.user

import cn.kzoj.common.minio.MinioBucketConfig
import cn.kzoj.exception.user.UsernameDuplicatedException
import cn.kzoj.exception.user.UsernameNotFoundException
import cn.kzoj.models.user.User
import io.ktor.server.plugins.NotFoundException
import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.io.InputStream
import java.util.UUID

class UserService(
    private val database: Database,
    private val minioClient: MinioClient
) {
    @Suppress("DuplicatedCode")
    suspend fun createUser(newUser: User): String =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            UserDAO.find {
                UserTable.username eq newUser.username
            }.let {
                if (!it.empty()) {
                    throw UsernameDuplicatedException()
                }
            }

            UserDAO.new {
                username = newUser.username
                encryptedPassword = newUser.plainPassword!! // TODO:加密
                school = newUser.school
                grade = newUser.grade
                realName = newUser.realName
                gender = newUser.gender
                githubHomepage = newUser.githubHomepage
                email = newUser.email
                avatarHashIndex = "" // TODO:设置默认头像
                authority = newUser.authority
                utcCreated = Clock.System.now()
                utcUpdated = this.utcCreated
            }
        }.id.value.toString()

    suspend fun queryUserByUUID(uuid: String): User =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            UserDAO.findById(UUID.fromString(uuid)).let {
                if (it == null) {
                    throw NotFoundException()
                }

                it.exposeWithoutPasswd()
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

        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(MinioBucketConfig.BucketNames.AVATARS)
                .`object`(avatarHashIndex)
                .stream(avatarFileInputStream, -1, 10485760)
                .contentType("image/jpg")
                .build()
        )

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
    fun getAvatar(userId: String): InputStream =
        minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(MinioBucketConfig.BucketNames.AVATARS)
                .`object`(userId)
                .build()
        )
}
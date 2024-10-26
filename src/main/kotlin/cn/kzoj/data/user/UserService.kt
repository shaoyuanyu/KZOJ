package cn.kzoj.data.user

import cn.kzoj.common.minio.MinioBucketConfig
import cn.kzoj.exception.user.UsernameDuplicatedException
import cn.kzoj.models.user.User
import io.minio.MinioClient
import io.minio.PutObjectArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.io.InputStream

class UserService(
    private val database: Database,
    private val minioClient: MinioClient
) {
    @Suppress("DuplicatedCode")
    suspend fun createUser(newUser: User): String =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            UserDAO.find {
                UserTable.username eq newUser.username
            }.count().let {
                if (it > 0) {
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
                utcCreated = Clock.System.now()
                utcUpdated = this.utcCreated
            }
        }.id.value.toString()

    // TODO:图像上传到minio，生成hash index
    // TODO:根据uuid更新UserTable中的AvatarHashIndex
    suspend fun uploadAvatar(uuid: String, avatarFileInputStream: InputStream) {
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(MinioBucketConfig.BucketNames.AVATARS)
                .`object`("a") // 生成hash index作为文件名
                .stream(avatarFileInputStream, -1, 10485760)
                .contentType("image/jpg")
                .build()
        )

        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
//            UserDAO.findByIdAndUpdate(UUID.fromString(uuid)) {
//            }
        }
    }

    suspend fun getAvatar(uuid: String) {}
}
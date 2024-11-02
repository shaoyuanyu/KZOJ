package cn.kzoj.persistence.database

import cn.kzoj.dto.user.UserAuthority
import cn.kzoj.persistence.database.problem.ProblemTable
import cn.kzoj.persistence.database.problemcase.ProblemCaseTable
import cn.kzoj.persistence.database.user.UserEntity
import cn.kzoj.persistence.database.user.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase(
    url: String,
    driver: String,
    user: String,
    password: String
): Database {
    // 配置HiKari
    val dataSource = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = url
            driverClassName = driver
            username = user
            this.password = password
            maximumPoolSize = 24
            isReadOnly = false
            transactionIsolation = "TRANSACTION_SERIALIZABLE"
        }
    )

    // 创建database实例
    val database = Database.connect(datasource=dataSource)

    // 第一次transaction与数据库建立连接
    // 检查table并创建缺失的，DSL "CREATE TABLE IF NOT EXISTS"
    transaction(database) {
        SchemaUtils.create(ProblemTable)
        SchemaUtils.create(ProblemCaseTable)
        SchemaUtils.create(UserTable)
    }

    // 若 User 表为空（新创建），则自动加入默认 admin 账户
    // TODO: 提醒管理员修改初始密码
    transaction(database) {
        if (UserEntity.all().empty()) {
            UserEntity.new {
                username = "admin"
                encryptedPassword = "12345" // TODO: 加密
                school = ""
                grade = 100
                realName = ""
                gender = ""
                githubHomepage = ""
                email = ""
                avatarHashIndex = "default" // TODO: 默认头像
                authority = UserAuthority.ADMIN
                utcCreated = Clock.System.now()
                utcUpdated = this.utcCreated
            }
        }
    }

    return database
}
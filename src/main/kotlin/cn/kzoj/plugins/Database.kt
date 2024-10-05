package cn.kzoj.plugins

import cn.kzoj.data.problem.Problems
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase(): Database {
    // 读取参数
    val url = environment.config.propertyOrNull("database.url")?.getString()
    val driver = environment.config.propertyOrNull("database.driver")?.getString()
    val user = environment.config.propertyOrNull("database.user")?.getString()
    val password = environment.config.propertyOrNull("database.password")?.getString()

    // 检验参数
    if (url.isNullOrEmpty()) {
        throw Exception("database url configuration does not exist or is empty.")
    }
    if (driver.isNullOrEmpty()) {
        throw Exception("database driver configuration does not exist or is empty.")
    }
    if (user.isNullOrEmpty()) {
        throw Exception("database user configuration does not exist or is empty.")
    }
    if (password.isNullOrEmpty()) {
        throw Exception("database password configuration does not exist or is empty.")
    }

    // 创建database实例
    // TODO: 使用连接池，如HikariCP
    val database = Database.connect(url = url, driver = driver, user = user, password = password)

    // 第一次transaction与数据库建立连接
    // 检查table并创建缺失的，DSL "CREATE TABLE IF NOT EXISTS"
    transaction(database) {
        SchemaUtils.create(Problems)
    }

    return database
}
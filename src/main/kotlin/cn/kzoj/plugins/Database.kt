package cn.kzoj.plugins

import cn.kzoj.data.problem.ProblemTable
import cn.kzoj.data.problemcase.ProblemCaseTable
import cn.kzoj.data.user.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
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

    // 配置HiKari
    val config = HikariConfig().apply {
        jdbcUrl = url
        driverClassName = driver
        username = user
        this.password = password
        maximumPoolSize = 6
        isReadOnly = false
        transactionIsolation = "TRANSACTION_SERIALIZABLE"
    }
    val dataSource = HikariDataSource(config)

    // 创建database实例
    // val database = Database.connect(url = url, driver = driver, user = user, password = password)
    val database = Database.connect(datasource=dataSource)

    // 第一次transaction与数据库建立连接
    // 检查table并创建缺失的，DSL "CREATE TABLE IF NOT EXISTS"
    transaction(database) {
        SchemaUtils.create(ProblemTable)
        SchemaUtils.create(ProblemCaseTable)
        SchemaUtils.create(UserTable)
    }

    return database
}
package cn.kzoj.persistence.database

import cn.kzoj.persistence.database.problem.ProblemTable
import cn.kzoj.persistence.database.problemcase.ProblemCaseTable
import cn.kzoj.persistence.database.user.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
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

    return database
}
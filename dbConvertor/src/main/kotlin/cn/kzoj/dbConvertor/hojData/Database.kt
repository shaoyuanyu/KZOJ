package cn.kzoj.dbConvertor.hojData

import cn.kzoj.dbConvertor.hojData.problem.ProblemTable
import cn.kzoj.dbConvertor.hojData.problemcase.ProblemCaseTable
import cn.kzoj.dbConvertor.hojData.user.UserInfoTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("DuplicatedCode")
fun configureHojDatabase(
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
            isReadOnly = true
            transactionIsolation = "TRANSACTION_SERIALIZABLE"
        }
    )

    // 创建database实例
    val database = Database.connect(datasource = dataSource)

    // 第一次transaction与数据库建立连接
    transaction(database) {
        SchemaUtils.create(ProblemTable)
        SchemaUtils.create(ProblemCaseTable)
        SchemaUtils.create(UserInfoTable)
    }

    return database
}
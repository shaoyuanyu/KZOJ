package cn.kzoj.data

import io.ktor.server.config.*
import org.ktorm.database.Database

class DatabaseProvider(config: ApplicationConfig) {
    private var database: Database

    init {
        // read database configurations
        val url = config.propertyOrNull("database.url")?.getString()
        val driver = config.propertyOrNull("database.driver")?.getString()
        val user = config.propertyOrNull("database.user")?.getString()
        val password = config.propertyOrNull("database.password")?.getString()

        // examine config
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

        // connect database
        database = Database.connect(url = url, driver = driver, user = user, password = password)
    }

    fun getDatabase(): Database = database
}
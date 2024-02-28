package cn.kzoj.plugins

import cn.kzoj.data.DatabaseProvider
import io.ktor.server.application.*
import org.ktorm.database.Database

fun Application.configureDatabase(): Database =
    DatabaseProvider(environment.config).getDatabase()
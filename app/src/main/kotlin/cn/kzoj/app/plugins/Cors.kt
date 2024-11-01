package cn.kzoj.app.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    // TODO: 仅开发环境
    install(CORS) {
        anyMethod()

        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowNonSimpleContentTypes = true

        allowCredentials = true
        allowSameOrigin = true

        anyHost()
    }
}
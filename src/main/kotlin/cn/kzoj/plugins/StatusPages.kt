package cn.kzoj.plugins

import cn.kzoj.exception.BasicNotFoundException
import cn.kzoj.exception.BasicBadRequestException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText

fun Application.configureStatusPages() {
    install(StatusPages) {
        // 404异常（某项资源未找到）
        exception<BasicNotFoundException> { call, cause ->
            call.respondText(text = cause.message.toString(), status = HttpStatusCode.NotFound)
        }

        // 参数异常（前端向后端传递的参数不符合标准或无效）
        exception<BasicBadRequestException> { call, cause ->
            call.respondText(text = cause.message.toString(), status = HttpStatusCode.BadRequest)
        }
    }
}
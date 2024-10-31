package cn.kzoj.web.plugins

import cn.kzoj.api.exception.basic.BasicNotFoundException
import cn.kzoj.api.exception.basic.BasicBadRequestException
import cn.kzoj.api.exception.basic.BasicUnauthorizedException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText

fun Application.configureStatusPages() {
    install(StatusPages) {
        // 授权异常（登录验证失败、访问未授权资源）
        exception<BasicUnauthorizedException> { call, cause ->
            call.respondText(text = cause.message.toString(), status = HttpStatusCode.Unauthorized)
        }

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
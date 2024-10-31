package cn.kzoj.web.plugins

import cn.kzoj.persistence.UserService
import cn.kzoj.api.exception.user.UserAccountValidateException
import cn.kzoj.api.exception.user.UserAuthorityException
import cn.kzoj.api.dto.user.UserAuthority
import cn.kzoj.api.dto.user.UserSession
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.form
import io.ktor.server.auth.session
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.directorySessionStorage
import java.io.File

fun Application.configureSecurity(userService: UserService) {
    // Sessions 模块
    val sessionStoragePath = environment.config.property("session.storage.path").getString()
    install(Sessions) {
        cookie<UserSession>("user_session", directorySessionStorage(File(sessionStoragePath))) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 600 // TODO: 约定有效期
        }
    }

    // Authentication 模块
    install(Authentication) {
        // web-form 验证（登录用）
        form("auth-form") {
            // 声明 web-form 中用户名和密码字段名
            userParamName = "username"
            passwordParamName = "password"

            // 验证账户
            validate { credentials ->
                val uuid: String? = userService.validate(credentials.name, credentials.password)

                if (uuid != null) {
                    UserIdPrincipal(uuid)
                } else {
                    null
                }
            }

            // 验证失败反馈
            challenge {
                throw UserAccountValidateException()
            }
        }

        // session 验证（user级）
        session<UserSession>("auth-session-user") {
            validate { session ->
                session
            }

            challenge {
                // call.respondRedirect("/user/login") // TODO: 重定向到登录页面
                throw UserAuthorityException()
            }
        }

        // session 验证（admin级）
        session<UserSession>("auth-session-admin") {
            validate { session ->
                if (session.userAuthority == UserAuthority.ADMIN) {
                    session
                } else {
                    throw UserAuthorityException()
                }
            }
        }
    }
}
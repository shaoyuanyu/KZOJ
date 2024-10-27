package cn.kzoj.routes

import cn.kzoj.data.user.UserService
import cn.kzoj.exception.user.UserAuthorityException
import cn.kzoj.models.user.User
import cn.kzoj.models.user.UserSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.request.receiveChannel
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.*
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import io.ktor.utils.io.jvm.javaio.toInputStream

fun Application.userRoutes(userService: UserService) {
    routing {
        route("/user") {
            // User CRUD（限管理员）
            authenticate("auth-session-admin") {
                createUser(userService)
            }

            // 普通用户注册/注销/更新账户/登录/登出
            authenticate("auth-form") {
                login(userService)
            }
            authenticate("auth-session-user") {
                logout()
            }

            // 头像操作
            authenticate("auth-session-user") {
                uploadAvatar(userService)
            }
        }
    }
}

/**
 * 管理员创建用户
 */
fun Route.createUser(userService: UserService) {
    post("/create") {
        val newUser = call.receive<User>()

        val uuid: String = userService.createUser(newUser)

        call.application.environment.log.info("new user created, uuid: $uuid")

        call.response.status(HttpStatusCode.Created)
    }
}

/**
 * 上传头像（图片文件）
 */
fun Route.uploadAvatar(userService: UserService) {
    post("/avatar/upload") {
        // TODO: 需要限制文件大小
        val avatarFileInputStream = call.receiveChannel().toInputStream()

        val userSession = call.sessions.get<UserSession>()
        if (userSession == null) {
            throw UserAuthorityException()
        }

        userService.uploadAvatar(userSession.userId, avatarFileInputStream)

        call.response.status(HttpStatusCode.OK)
    }
}

/**
 * 用户登录
 */
fun Route.login(userService: UserService) {
    post("/login") {
        val uuid = call.principal<UserIdPrincipal>()?.name.toString()
        val user = userService.queryUserByUUID(uuid)

        println(user)

        call.sessions.set(
            UserSession(userId = uuid, username = user.username, userAuthority = user.authority)
        )

        call.respondRedirect("/home") // TODO: 重定向到主页
        call.response.status(HttpStatusCode.OK)
    }
}

fun Route.logout() {
    post("/logout") {
        call.sessions.clear<UserSession>()
        call.respondRedirect("/login") // TODO: 重定向到登录页
    }
}
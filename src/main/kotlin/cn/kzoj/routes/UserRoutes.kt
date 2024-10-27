package cn.kzoj.routes

import cn.kzoj.data.user.UserService
import cn.kzoj.exception.user.UserAuthorityException
import cn.kzoj.models.user.User
import cn.kzoj.models.user.UserAuthority
import cn.kzoj.models.user.UserSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.request.receiveChannel
import io.ktor.server.response.respond
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
            // 无权限限制
            signup(userService) // 注册
            getAvatar(userService) // 获取头像

            authenticate("auth-form") {
                login(userService) // 登录
            }

            // 普通用户
            authenticate("auth-session-user") {
                updateSelfInfo(userService) // 更新自己的信息
                logout() // 登出
                uploadAvatar(userService) // 上传头像
            }

            // 管理员
            authenticate("auth-session-admin") {
                createUser(userService) // 创建用户
                updateUser(userService) // 更新用户信息
                deleteUser(userService) // 删除用户
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

        userService.createUser(newUser)

        call.response.status(HttpStatusCode.Created)
    }
}

/**
 * 管理员更新用户信息
 */
fun Route.updateUser(userService: UserService) {
    post("/update") {
        val newUser = call.receive<User>()

        userService.updateUser(newUser)

        call.response.status(HttpStatusCode.OK)
    }
}

/**
 * 管理员删除用户
 */
fun Route.deleteUser(userService: UserService) {
    delete("/{id}") {
        val id = call.parameters["id"].toString()

        userService.deleteUser(id)

        call.response.status(HttpStatusCode.OK)
    }
}

/**
 * 用户注册
 *
 * 注册并登录
 */
fun Route.signup(userService: UserService) {
    post("/signup") {
        val newUser = call.receive<User>().copy(
            authority = UserAuthority.USER
        )

        val uuid = userService.createUser(newUser)

        call.sessions.set(
            UserSession(userId = uuid, username = newUser.username, userAuthority = UserAuthority.USER)
        )

        call.respondRedirect("/home") // TODO: 重定向到主页
        call.response.status(HttpStatusCode.Created)
    }
}

/**
 * 用户信息更新（普通用户更新自己的信息）
 */
fun Route.updateSelfInfo(userService: UserService) {
    post("/self/update") {
        val userSession = call.sessions.get<UserSession>()
        if (userSession == null) {
            throw UserAuthorityException()
        }

        val newUser = call.receive<User>().copy(
            uuid = userSession.userId,
            authority = userSession.userAuthority
        )

        userService.updateUser(newUser)

        call.respondRedirect("/me") // TODO: 重定向到用户信息页
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

        call.sessions.set(
            UserSession(userId = uuid, username = user.username, userAuthority = user.authority)
        )

        call.respondRedirect("/home") // TODO: 重定向到主页
        call.response.status(HttpStatusCode.OK)
    }
}

/**
 * 用户登出
 */
fun Route.logout() {
    post("/logout") {
        call.sessions.clear<UserSession>()
        call.respondRedirect("/login") // TODO: 重定向到登录页
    }
}

/**
 * 上传头像（图片文件）
 */
fun Route.uploadAvatar(userService: UserService) {
    post("/avatar") {
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
 * 获取头像（输入流）
 */
fun Route.getAvatar(userService: UserService) {
    get("/avatar/{id}") {
        val id = call.parameters["id"].toString()

        call.respond(
            userService.getAvatar(id)
        )
    }
}
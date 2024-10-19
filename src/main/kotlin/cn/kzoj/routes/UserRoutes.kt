package cn.kzoj.routes

import cn.kzoj.data.user.UserService
import cn.kzoj.models.user.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.request.receiveChannel
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import io.ktor.utils.io.jvm.javaio.toInputStream

fun Application.userRoutes(userService: UserService) {
    routing {
        route("/user") {
            // User CRUD
            createUser(userService)

            // 头像操作
            uploadAvatar(userService)
        }
    }
}

/**
 * 创建用户
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
        // TODO: 读Session
        // TODO: 需要限制文件大小
        val avatarFileInputStream = call.receiveChannel().toInputStream()

        userService.uploadAvatar(avatarFileInputStream)

        call.respondText("A file is uploaded")
    }
}
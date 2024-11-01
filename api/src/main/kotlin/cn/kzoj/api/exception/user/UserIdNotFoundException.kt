package cn.kzoj.api.exception.user

import cn.kzoj.api.exception.basic.BasicNotFoundException

class UserIdNotFoundException: BasicNotFoundException(message = "USER_ID_NOT_FOUND")
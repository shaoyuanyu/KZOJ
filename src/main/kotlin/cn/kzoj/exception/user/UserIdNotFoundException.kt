package cn.kzoj.exception.user

import cn.kzoj.exception.basic.BasicNotFoundException

class UserIdNotFoundException: BasicNotFoundException(message = "USER_ID_NOT_FOUND")
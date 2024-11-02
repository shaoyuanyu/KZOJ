package cn.kzoj.dto.exception.user

import cn.kzoj.dto.exception.basic.BasicNotFoundException

class UserIdNotFoundException : BasicNotFoundException(message = "USER_ID_NOT_FOUND")
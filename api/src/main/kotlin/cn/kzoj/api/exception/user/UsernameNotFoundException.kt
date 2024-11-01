package cn.kzoj.api.exception.user

import cn.kzoj.api.exception.basic.BasicNotFoundException

class UsernameNotFoundException: BasicNotFoundException(message = "USERNAME_NOT_FOUND")
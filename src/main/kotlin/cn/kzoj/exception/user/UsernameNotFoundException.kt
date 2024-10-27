package cn.kzoj.exception.user

import cn.kzoj.exception.basic.BasicNotFoundException

class UsernameNotFoundException: BasicNotFoundException(message = "USERNAME_NOT_FOUND")
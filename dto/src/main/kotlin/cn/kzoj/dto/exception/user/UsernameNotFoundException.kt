package cn.kzoj.dto.exception.user

import cn.kzoj.dto.exception.basic.BasicNotFoundException

class UsernameNotFoundException: BasicNotFoundException(message = "USERNAME_NOT_FOUND")
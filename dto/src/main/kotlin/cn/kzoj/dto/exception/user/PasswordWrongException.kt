package cn.kzoj.dto.exception.user

import cn.kzoj.dto.exception.basic.BasicUnauthorizedException

class PasswordWrongException() : BasicUnauthorizedException(message = "WRONG_PASSWORD")
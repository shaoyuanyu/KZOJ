package cn.kzoj.dto.exception.user

import cn.kzoj.dto.exception.basic.BasicUnauthorizedException

class UserAuthorityException : BasicUnauthorizedException(message = "USER_AUTHORITY_NOT_QUALIFIED")
package cn.kzoj.exception.user

import cn.kzoj.exception.basic.BasicUnauthorizedException

class UserAuthorityException: BasicUnauthorizedException(message = "USER_AUTHORITY_NOT_QUALIFIED")
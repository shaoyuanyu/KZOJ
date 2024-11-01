package cn.kzoj.api.exception.user

import cn.kzoj.api.exception.basic.BasicUnauthorizedException

class UserAuthorityException: BasicUnauthorizedException(message = "USER_AUTHORITY_NOT_QUALIFIED")
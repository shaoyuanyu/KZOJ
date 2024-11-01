package cn.kzoj.dto.exception.user

import cn.kzoj.dto.exception.basic.BasicUnauthorizedException

class UserAccountValidateException: BasicUnauthorizedException(message = "USER_ACCOUNT_VALIDATION_FAILED")
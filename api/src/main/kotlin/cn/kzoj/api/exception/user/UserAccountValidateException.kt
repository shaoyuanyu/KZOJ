package cn.kzoj.api.exception.user

import cn.kzoj.api.exception.basic.BasicUnauthorizedException

class UserAccountValidateException: BasicUnauthorizedException(message = "USER_ACCOUNT_VALIDATION_FAILED")
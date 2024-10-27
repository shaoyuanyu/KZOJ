package cn.kzoj.exception.user

import cn.kzoj.exception.basic.BasicUnauthorizedException

class UserAccountValidateException: BasicUnauthorizedException(message = "USER_ACCOUNT_VALIDATION_FAILED")
package cn.kzoj.api.exception.user

import cn.kzoj.api.exception.basic.BasicBadRequestException

class UsernameDuplicatedException: BasicBadRequestException(message = "USERNAME_DUPLICATED")
package cn.kzoj.exception.user

import cn.kzoj.exception.BasicBadRequestException

class UsernameDuplicatedException: BasicBadRequestException(message = "USERNAME_DUPLICATED")
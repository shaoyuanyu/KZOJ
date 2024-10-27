package cn.kzoj.exception.user

import cn.kzoj.exception.basic.BasicBadRequestException

class UsernameDuplicatedException: BasicBadRequestException(message = "USERNAME_DUPLICATED")
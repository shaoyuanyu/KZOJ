package cn.kzoj.dto.exception.user

import cn.kzoj.dto.exception.basic.BasicBadRequestException

class UsernameDuplicatedException: BasicBadRequestException(message = "USERNAME_DUPLICATED")
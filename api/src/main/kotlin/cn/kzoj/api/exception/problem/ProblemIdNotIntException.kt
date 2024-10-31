package cn.kzoj.api.exception.problem

import cn.kzoj.api.exception.basic.BasicBadRequestException

class ProblemIdNotIntException: BasicBadRequestException(message = "PROBLEM_ID_SHOULD_BE_INT")
package cn.kzoj.exception.problem

import cn.kzoj.exception.BasicBadRequestException

class ProblemIdNotIntException: BasicBadRequestException(message = "PROBLEM_ID_SHOULD_BE_INT")
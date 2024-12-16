package cn.kzoj.dto.exception.problem

import cn.kzoj.dto.exception.basic.BasicBadRequestException

class ProblemIdNotIntException : BasicBadRequestException(message = "PROBLEM_ID_SHOULD_BE_INT")
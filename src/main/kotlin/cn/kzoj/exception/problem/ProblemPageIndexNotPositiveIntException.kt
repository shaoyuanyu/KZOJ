package cn.kzoj.exception.problem

import cn.kzoj.exception.basic.BasicBadRequestException

class ProblemPageIndexNotPositiveIntException: BasicBadRequestException(message = "PROBLEM_PAGE_INDEX_SHOULD_BE_POSITIVE_INT")
package cn.kzoj.api.exception.problem

import cn.kzoj.api.exception.basic.BasicBadRequestException

class ProblemPageIndexOutOfRangeException: BasicBadRequestException(message = "PROBLEM_PAGE_INDEX_OUT_OF_RANGE")
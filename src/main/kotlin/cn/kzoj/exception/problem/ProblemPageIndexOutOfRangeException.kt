package cn.kzoj.exception.problem

import cn.kzoj.exception.BasicBadRequestException

class ProblemPageIndexOutOfRangeException: BasicBadRequestException(message = "PROBLEM_PAGE_INDEX_OUT_OF_RANGE")
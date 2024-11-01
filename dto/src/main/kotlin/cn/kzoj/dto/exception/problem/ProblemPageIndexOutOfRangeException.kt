package cn.kzoj.dto.exception.problem

import cn.kzoj.dto.exception.basic.BasicBadRequestException

class ProblemPageIndexOutOfRangeException: BasicBadRequestException(message = "PROBLEM_PAGE_INDEX_OUT_OF_RANGE")
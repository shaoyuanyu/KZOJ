package cn.kzoj.dto.exception.problem

import cn.kzoj.dto.exception.basic.BasicBadRequestException

class ProblemPageIndexNotPositiveIntException :
    BasicBadRequestException(message = "PROBLEM_PAGE_INDEX_SHOULD_BE_POSITIVE_INT")
package cn.kzoj.api.exception.problem

import cn.kzoj.api.exception.basic.BasicNotFoundException

class ProblemIdNotFoundException: BasicNotFoundException(message = "PROBLEM_ID_NOT_FOUND")
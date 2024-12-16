package cn.kzoj.dto.exception.problem

import cn.kzoj.dto.exception.basic.BasicNotFoundException

class ProblemIdNotFoundException : BasicNotFoundException(message = "PROBLEM_ID_NOT_FOUND")
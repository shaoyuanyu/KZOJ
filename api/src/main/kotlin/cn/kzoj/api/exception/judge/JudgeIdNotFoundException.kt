package cn.kzoj.api.exception.judge

import cn.kzoj.api.exception.basic.BasicNotFoundException

class JudgeIdNotFoundException: BasicNotFoundException(message = "JUDGE_ID_NOT_FOUND")
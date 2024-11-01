package cn.kzoj.dto.exception.judge

import cn.kzoj.dto.exception.basic.BasicNotFoundException

class JudgeIdNotFoundException: BasicNotFoundException(message = "JUDGE_ID_NOT_FOUND")
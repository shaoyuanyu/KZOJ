package cn.kzoj.data.problem

import cn.kzoj.data.user.User

data class JudgeRequest(
    val problemId: String,
    val userId: String,
    val judgeId: String,
    val submittedCode: String,
)
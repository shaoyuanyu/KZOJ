package cn.kzoj.data.problem

import java.time.LocalDateTime

data class ProblemDetail(
    /**
     * 编号
     */
    var problemId: String,

    /**
     * 标题
     */
    var title: String,

    /**
     * 作者
     */
    var author: String,

    /**
     * 类型，如ACM/OI/...
     */
    var problemType: Int,

    /**
     * 时间限制，单位ms
     */
    var timeLimit: Int,

    /**
     * 空间限制，单位mb
     */
    var memoryLimit: Int,

    /**
     * 栈限制，单位mb
     */
    var stackLimit: Int,

    /**
     * 题目描述
     */
    var problemDescription: String,

    /**
     * 输入描述
     */
    var inputDescription: String,

    /**
     * 输出描述
     */
    var outputDescription: String,

    /**
     * 题面样例
     */
    var examples: String,

    /**
     * 是否为vj判题
     */
    var isRemoteJudge: Boolean,

    /**
     * 题目来源
     *
     * vj判题时例如：HDU-1000的链接
     */
    var problemSource: String,

    /**
     * 题目难度
     */
    var difficulty: Int,

    /**
     * 备注/提醒
     */
    var hint: String,

    /**
     * 当该题目为oi题目时的分数
     */
    var oiScore: Int,

    /**
     * 创建日期
     */
    var dateCreated: LocalDateTime,

    /**
     * 修改日期
     */
    var dateLastModified: LocalDateTime,
)

fun Problem.toProblemDetail(): ProblemDetail {
    return ProblemDetail(
        problemId = this.problemId,
        title = this.title,
        author = this.author,
        problemType = this.problemType,
        timeLimit = this.timeLimit,
        memoryLimit = this.memoryLimit,
        stackLimit = this.stackLimit,
        problemDescription = this.problemDescription,
        inputDescription = this.inputDescription,
        outputDescription = this.outputDescription,
        examples = this.examples,
        isRemoteJudge = this.isRemoteJudge,
        problemSource = this.problemSource,
        difficulty = this.difficulty,
        hint = this.hint,
        oiScore = this.oiScore,
        dateCreated = this.dateCreated,
        dateLastModified = this.dateLastModified
    )
}
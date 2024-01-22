package cn.kzoj.data.problem

import org.ktorm.entity.Entity
import java.time.LocalDateTime

/**
 * Ktorm仍推荐以interface（而不是data class）作为实体类
 *
 * 对于抽象的数据结构T，使用interface T声明其结构，并作为Ktorm中的实体类
 *
 * 本项目当前使用Ktorm，但保留未来迁移到Exposed正式版本的期望
 */
interface Problem: Entity<Problem> {
    companion object : Entity.Factory<Problem>()

    /**
     * id（主键）
     */
    val id: Int

    /**
     * 编号
     */
    var problemId: String

    /**
     * 标题
     */
    var title: String

    /**
     * 作者
     */
    var author: String

    /**
     * 类型，如ACM/OI/...
     */
    // TODO:实现type支持自定义增删改
    var problemType: Int

    /**
     * 时间限制，单位ms
     */
    var timeLimit: Int

    /**
     * 空间限制，单位mb
     */
    var memoryLimit: Int

    /**
     * 栈限制，单位mb
     */
    var stackLimit: Int

    /**
     * 题目描述
     */
    var problemDescription: String

    /**
     * 输入描述
     */
    var inputDescription: String

    /**
     * 输出描述
     */
    var outputDescription: String

    /**
     * 题面样例
     */
    // TODO:List<String>如何映射
    var examples: String

    /**
     * 是否为vj判题
     */
    var isRemoteJudge: Boolean

    /**
     * 题目来源
     *
     * vj判题时例如：HDU-1000的链接
     */
    var problemSource: String

    /**
     * 题目难度
     */
    // TODO:实现难度支持自定义
    var difficulty: Int

    /**
     * 备注/提醒
     */
    var hint: String

    /**
     * 公开(默认)/私有/比赛中
     */
    var authority: String // ProblemAuthority

    /**
     * 当该题目为oi题目时的分数
     */
    var oiScore: Int

    /**
     * 该题目对应的相关提交代码，用户是否可用分享
     */
    var isCodeSharable: Boolean

    /**
     * 判题模式，如default/spj/interactive/...
     */
    var judgeMode: String

    /**
     * 判例模式，如default/subtask_lowest/subtask_average/...
     */
    var judgeCaseMode: String

    /**
     * 特判代码
     */
    var spjCode: String?

    /**
     * 特判语言
     */
    var spjLanguage: String?

    /**
     * 是否默认去除用户代码的每行末尾空白符
     */
    var removeEndBlankChar: Boolean

    /**
     * 是否默认开启该题目的测试样例结果查看
     */
    var openCaseResult: Boolean

    /**
     * 题目测试数据是否是上传的
     */
    var isCaseUploaded: Boolean

    /**
     * 测试数据的版本
     */
    var caseVersion: String

    /**
     * 修改题目的用户的用户名
     */
    var lastModifiedByUser: String

    /**
     * 是否为团队内的题目
     */
    // TODO:移除团队模式
    var isInGroup: Boolean

    /**
     * 团队id
     */
    var groupId: Int?

    /**
     * 创建日期
     */
    var dateCreated: LocalDateTime

    /**
     * 修改日期
     */
    var dateLastModified: LocalDateTime
}
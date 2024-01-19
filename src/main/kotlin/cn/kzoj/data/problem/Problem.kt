package cn.kzoj.data.problem

import org.ktorm.entity.Entity
import java.util.*

/**
 * Ktorm仍推荐以interface（而不是data class）作为实体类
 *
 * 对于抽象的数据结构T，使用interface T声明其结构，并作为Ktorm中的实体类
 *
 * 本项目当前使用Ktorm，但保留未来迁移到Exposed正式版本的期望
 */
interface Problem: Entity<Problem> {
    /**
     * id
     */
    val id: Int

    /**
     * 编号
     */
    val problemId: String

    /**
     * 标题
     */
    val title: String

    /**
     * 作者
     */
    val author: String

    /**
     * 类型，如ACM/OI/...
     */
    // TODO:实现type支持自定义增删改
    val type: Int

    /**
     * 判题模式，如default/spj/interactive/...
     */
    val judgeMode: String

    /**
     * 判例模式，如default/subtask_lowest/subtask_average/...
     */
    val judgeCaseMode: String

    /**
     * 时间限制，单位ms
     */
    val timeLimit: Int

    /**
     * 空间限制，单位mb
     */
    val memoryLimit: Int

    /**
     * 栈限制，单位mb
     */
    val stackLimit: Int

    /**
     * 描述
     */
    val description: String

    /**
     * 输入描述
     */
    // TODO:下次更新修改为inputDescription
    val input: String

    /**
     * 输出描述
     */
    // TODO:下次更新修改为outputDescription
    val output: String

    /**
     * 题面样例
     */
    // TODO:下次更新修改类型为List<String>
    val examples: String

    /**
     * 是否为vj判题
     */
    // TODO:下次更新修改为isRemoteJudge
    val isRemote: Boolean

    /**
     * 题目来源
     *
     * vj判题时例如：HDU-1000的链接
     */
    val source: String

    /**
     * 题目难度
     */
    // TODO:实现难度支持自定义
    val difficulty: Int

    /**
     * 备注/提醒
     */
    val hint: String

    /**
     * 1公开，2私有，3比赛中
     *
     * 默认为1
     */
    // TODO:用更清晰的enum class而不是Int实现
    val auth: Int

    /**
     * 当该题目为oi题目时的分数
     */
    val ioScore: Int

    /**
     * 该题目对应的相关提交代码，用户是否可用分享
     */
    // TODO:下次更新修改为isCodeSharable
    val codeShare: Boolean

    /**
     * 是否默认去除用户代码的每行末尾空白符
     */
    // TODO:下次更新修改为removeEndBlank
    val isRemoveEndBlank: Boolean

    /**
     * 是否默认开启该题目的测试样例结果查看
     */
    val openCaseResult: Boolean

    /**
     * 题目测试数据是否是上传的
     */
    // TODO:下次更新修改为isCaseUploaded
    val isUploadCase: Boolean

    /**
     * 测试数据的版本
     */
    val caseVersion: String

    /**
     * 修改题目的用户的用户名
     */
    val modifiedUser: String

    /**
     * 是否为团队内的题目
     */
    // TODO:下次更新修改为isInGroup
    val isGroup: Boolean

    /**
     * 团队id
     */
    // TODO:下次更新修改为groupId
    val gid: Long

    /**
     * 申请公开的进度
     *
     * 0为未申请，1为申请中，2为申请通过，3为申请拒绝
     */
    // TODO:用更清晰的enum class而不是Int实现
    val applyPublicProgress: Int

    /**
     * 是否是file io自定义输入输出文件模式
     */
    val isFileIO: Boolean

    /**
     * 题目指定的file io输入文件的名称
     */
    val ioReadFileName: String

    /**
     * 题目指定的file io输出文件的名称
     */
    val ioWriteFileName: String

    /**
     * 创建日期
     */
    // TODO:下次更新修改为createdDate
    val gmtCreate: Date

    /**
     * 修改日期
     */
    // TODO:下次更新修改为modifiedDate
    val gmtModified: Date
}

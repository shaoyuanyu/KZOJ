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
    var problem_id: String

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
    var problem_type: Int

    /**
     * 时间限制，单位ms
     */
    var time_limit: Int

    /**
     * 空间限制，单位mb
     */
    var memory_limit: Int

    /**
     * 栈限制，单位mb
     */
    var stack_limit: Int

    /**
     * 题目描述
     */
    var problem_description: String

    /**
     * 输入描述
     */
    var input_description: String

    /**
     * 输出描述
     */
    var output_description: String

    /**
     * 题面样例
     */
    // TODO:List<String>如何映射
    var examples: String

    /**
     * 是否为vj判题
     */
    var is_remote_judge: Boolean

    /**
     * 题目来源
     *
     * vj判题时例如：HDU-1000的链接
     */
    var problem_source: String

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
    var oi_score: Int

    /**
     * 该题目对应的相关提交代码，用户是否可用分享
     */
    var is_code_sharable: Boolean

    /**
     * 判题模式，如default/spj/interactive/...
     */
    var judge_mode: String

    /**
     * 判例模式，如default/subtask_lowest/subtask_average/...
     */
    var judge_case_mode: String

    /**
     * 特判代码
     */
    var spj_code: String?

    /**
     * 特判语言
     */
    var spj_language: String?

    /**
     * 是否默认去除用户代码的每行末尾空白符
     */
    var remove_end_blank_char: Boolean

    /**
     * 是否默认开启该题目的测试样例结果查看
     */
    var open_case_result: Boolean

    /**
     * 题目测试数据是否是上传的
     */
    var is_case_uploaded: Boolean

    /**
     * 测试数据的版本
     */
    var case_version: String

    /**
     * 修改题目的用户的用户名
     */
    var last_modified_by_user: String

    /**
     * 是否为团队内的题目
     */
    var is_in_group: Boolean

    /**
     * 团队id
     */
    var group_id: Int?

    /**
     * 创建日期
     */
    var date_created: LocalDateTime

    /**
     * 修改日期
     */
    var date_last_modified: LocalDateTime
}
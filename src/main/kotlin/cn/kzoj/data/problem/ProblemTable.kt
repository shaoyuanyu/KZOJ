package cn.kzoj.data.problem

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ProblemTable: IntIdTable("exposed_problem") {

    val title: Column<String> = varchar("title", 100).index()

    val author: Column<String> = varchar("author", 100)

    val createdByUser: Column<String> = varchar("created_by_user", 100)

    val description: Column<String> = text("description")

    val timeLimit: Column<Long> = long("time_limit")

    val memoryLimit: Column<Long> = long("memory_limit")

    val stackLimit: Column<Long> = long("stack_limit")

    val inputDescription: Column<String> = text("input_description")

    val outputDescription: Column<String> = text("output_description")

    /**
     * 样例
     * 格式为 <input></input><output></output>
     * 可包含多组样例，由前端解析
     */
    val examples: Column<String> = text("examples")

    val problemSource: Column<String> = varchar("problem_source", 100)

    val difficulty: Column<Int> = integer("difficulty")

    val tip: Column<String> = text("tip")

    /**
     * 题目状态
     * 公开（默认）/私有/比赛中
     */
    val status: Column<String> = varchar("status", 50)

    val score: Column<Int> = integer("score")

    val localTimeCreated: Column<LocalDateTime> = datetime("gmt_created")

    val localTimeLastModified: Column<LocalDateTime> = datetime("gmt_last_modified")
}
package cn.kzoj.dbConvertor.hojData.problemcase

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object ProblemCaseTable: IntIdTable("problem_case") {

    val pid: Column<Int> = integer("pid")

    val input: Column<String> = text("input")

    val output: Column<String> = text("output")

    val score: Column<Int> = integer("score")

    val status: Column<Int> = integer("status")

    val groupNum: Column<Int> = integer("group_num")

    val gmtCreated: Column<String> = text("gmt_create")

    val gmtModified: Column<String> = text("gmt_modified")
}
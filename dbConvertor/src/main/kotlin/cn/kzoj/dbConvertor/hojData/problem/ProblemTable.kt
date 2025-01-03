package cn.kzoj.dbConvertor.hojData.problem

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ProblemTable : IntIdTable("problem") {

    val problemId: Column<String?> = text("problem_id").nullable()

    val title: Column<String?> = text("title").nullable()

    val author: Column<String?> = text("author").nullable()

    val problemType: Column<Int> = integer("problem_type")

    val timeLimit: Column<Long> = long("time_limit")

    val memoryLimit: Column<Long> = long("memory_limit")

    val stackLimit: Column<Long> = long("stack_limit")

    val problemDescription: Column<String?> = text("problem_description").nullable()

    val inputDescription: Column<String?> = text("input_description").nullable()

    val outputDescription: Column<String?> = text("output_description").nullable()

    val examples: Column<String?> = text("examples").nullable()

    val isRemoteJudge: Column<Boolean> = bool("is_remote_judge")

    val problemSource: Column<String?> = text("problem_source").nullable()

    val difficulty: Column<Int> = integer("difficulty")

    val hint: Column<String?> = text("hint").nullable()

    val authority: Column<Int> = integer("authority")

    val oiScore: Column<Int> = integer("oi_score")

    val isCodeSharable: Column<Boolean> = bool("is_code_sharable")

    val judgeMode: Column<String?> = text("judge_mode").nullable()

    val judgeCaseMode: Column<String?> = text("judge_case_mode").nullable()

    val spjCode: Column<String?> = text("spj_code").nullable()

    val spjLanguage: Column<String?> = text("spj_language").nullable()

    val removeEndBlankChar: Column<Boolean> = bool("remove_end_blank_char")

    val openCaseResult: Column<Boolean> = bool("open_case_result")

    val isCaseUploaded: Column<Boolean> = bool("is_case_uploaded")

    val caseVersion: Column<String?> = text("case_version").nullable()

    val lastModifiedByUser: Column<String?> = text("last_modified_by_user").nullable()

    val isInGroup: Column<Boolean> = bool("is_in_group")

    val groupId: Column<Int?> = integer("group_id").nullable()

    val dateCreated: Column<LocalDateTime> = datetime("date_created")

    val dateLastModified: Column<LocalDateTime> = datetime("date_last_modified")
}
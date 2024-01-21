package cn.kzoj.data.problem

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*

object Problems : Table<Problem>("problem") {
    val id = int(ProblemTableColumn.id).primaryKey().bindTo { it.id }
    val problem_id = text(ProblemTableColumn.problem_id).bindTo { it.problem_id }
    val title = text(ProblemTableColumn.title).bindTo { it.title }
    val author = text(ProblemTableColumn.author).bindTo { it.author }
    val problem_type = int(ProblemTableColumn.problem_type).bindTo { it.problem_type }
    val time_limit = int(ProblemTableColumn.time_limit).bindTo { it.time_limit }
    val memory_limit = int(ProblemTableColumn.memory_limit).bindTo { it.memory_limit }
    val stack_limit = int(ProblemTableColumn.stack_limit).bindTo { it.stack_limit }
    val problem_description = text(ProblemTableColumn.problem_description).bindTo { it.problem_description }
    val input_description = text(ProblemTableColumn.input_description).bindTo { it.input_description }
    val output_description = text(ProblemTableColumn.output_description).bindTo { it.output_description }

    val examples = text(ProblemTableColumn.examples).bindTo { it.examples }

    val is_remote_judge = boolean(ProblemTableColumn.is_remote_judge).bindTo { it.is_remote_judge }
    val problem_source = text(ProblemTableColumn.problem_source).bindTo { it.problem_source }
    val difficulty = int(ProblemTableColumn.difficulty).bindTo { it.difficulty }
    val hint = text(ProblemTableColumn.hint).bindTo { it.hint }
    val authority = text(ProblemTableColumn.authority).bindTo { it.authority }
    val oi_score = int(ProblemTableColumn.oi_score).bindTo { it.oi_score }
    val is_code_sharable = boolean(ProblemTableColumn.is_code_sharable).bindTo { it.is_code_sharable }
    val judge_mode = text(ProblemTableColumn.judge_mode).bindTo { it.judge_mode }
    val judge_case_mode = text(ProblemTableColumn.judge_case_mode).bindTo { it.judge_case_mode }
    val spj_code = text(ProblemTableColumn.spj_code).bindTo { it.spj_code }
    val spj_language = text(ProblemTableColumn.spj_language).bindTo { it.spj_language }
    val remove_end_blank_char = boolean(ProblemTableColumn.remove_end_blank_char).bindTo { it.remove_end_blank_char }
    val open_case_result = boolean(ProblemTableColumn.open_case_result).bindTo { it.open_case_result }
    val is_case_uploaded = boolean(ProblemTableColumn.is_case_uploaded).bindTo { it.is_case_uploaded }
    val case_version = text(ProblemTableColumn.case_version).bindTo { it.case_version }
    val last_modified_by_user = text(ProblemTableColumn.last_modified_by_user).bindTo { it.last_modified_by_user }
    val is_in_group = boolean(ProblemTableColumn.is_in_group).bindTo { it.is_in_group }
    val group_id = int(ProblemTableColumn.group_id).bindTo { it.group_id }
    val date_created = datetime(ProblemTableColumn.date_created).bindTo { it.date_created }
    val date_last_modified = datetime(ProblemTableColumn.date_last_modified).bindTo { it.date_last_modified }

    val Database.problems get() = this.sequenceOf(Problems)
}

object ProblemTableColumn {
    const val id = "id"
    const val problem_id = "problem_id"
    const val title = "title"
    const val author = "author"
    const val problem_type = "problem_type"
    const val time_limit = "time_limit"
    const val memory_limit = "memory_limit"
    const val stack_limit = "stack_limit"
    const val problem_description = "problem_description"
    const val input_description = "input_description"
    const val output_description = "output_description"

    const val examples = "examples"

    const val is_remote_judge = "is_remote_judge"
    const val problem_source = "problem_source"
    const val difficulty = "difficulty"
    const val hint = "hint"
    const val authority = "authority"
    const val oi_score = "oi_score"
    const val is_code_sharable = "is_code_sharable"
    const val judge_mode = "judge_mode"
    const val judge_case_mode = "judge_case_mode"
    const val spj_code = "spj_code"
    const val spj_language = "spj_language"
    const val remove_end_blank_char = "remove_end_blank_char"
    const val open_case_result = "open_case_result"
    const val is_case_uploaded = "is_case_uploaded"
    const val case_version = "case_version"
    const val last_modified_by_user = "last_modified_by_user"
    const val is_in_group = "is_in_group"
    const val group_id = "group_id"
    const val date_created = "date_created"
    const val date_last_modified = "date_last_modified"
}
package cn.kzoj.data.problem

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*

object Problems : Table<Problem>("problem") {
    val id = int(ProblemTableColumn.ID).primaryKey().bindTo { it.id }
    val problem_id = text(ProblemTableColumn.PROBLEM_ID).bindTo { it.problemId }
    val title = text(ProblemTableColumn.TITLE).bindTo { it.title }
    val author = text(ProblemTableColumn.AUTHOR).bindTo { it.author }
    val created_by_user = text(ProblemTableColumn.CREATED_BY_USER).bindTo { it.createdByUser }
    val last_modified_by_user = text(ProblemTableColumn.LAST_MODIFIED_BY_USER).bindTo { it.lastModifiedByUser }
    val problem_type = int(ProblemTableColumn.PROBLEM_TYPE).bindTo { it.problemType }
    val time_limit = int(ProblemTableColumn.TIME_LIMIT).bindTo { it.timeLimit }
    val memory_limit = int(ProblemTableColumn.MEMORY_LIMIT).bindTo { it.memoryLimit }
    val stack_limit = int(ProblemTableColumn.STACK_LIMIT).bindTo { it.stackLimit }
    val problem_description = text(ProblemTableColumn.PROBLEM_DESCRIPTION).bindTo { it.problemDescription }
    val input_description = text(ProblemTableColumn.INPUT_DESCRIPTION).bindTo { it.inputDescription }
    val output_description = text(ProblemTableColumn.OUTPUT_DESCRIPTION).bindTo { it.outputDescription }

    val examples = text(ProblemTableColumn.EXAMPLES).bindTo { it.examples }

    val is_remote_judge = boolean(ProblemTableColumn.IS_REMOTE_JUDGE).bindTo { it.isRemoteJudge }
    val problem_source = text(ProblemTableColumn.PROBLEM_SOURCE).bindTo { it.problemSource }
    val difficulty = int(ProblemTableColumn.DIFFICULTY).bindTo { it.difficulty }
    val hint = text(ProblemTableColumn.HINT).bindTo { it.hint }
    val authority = text(ProblemTableColumn.AUTHORITY).bindTo { it.authority }
    val oi_score = int(ProblemTableColumn.OI_SCORE).bindTo { it.oiScore }
    val is_code_sharable = boolean(ProblemTableColumn.IS_CODE_SHARABLE).bindTo { it.isCodeSharable }
    val judge_mode = text(ProblemTableColumn.JUDGE_MODE).bindTo { it.judgeMode }
    val judge_case_mode = text(ProblemTableColumn.JUDGE_CASE_MODE).bindTo { it.judgeCaseMode }
    val spj_code = text(ProblemTableColumn.SPJ_CODE).bindTo { it.spjCode }
    val spj_language = text(ProblemTableColumn.SPJ_LANGUAGE).bindTo { it.spjLanguage }
    val remove_end_blank_char = boolean(ProblemTableColumn.REMOVE_END_BLANK_CHAR).bindTo { it.removeEndBlankChar }
    val open_case_result = boolean(ProblemTableColumn.OPEN_CASE_RESULT).bindTo { it.openCaseResult }
    val is_case_uploaded = boolean(ProblemTableColumn.IS_CASE_UPLOADED).bindTo { it.isCaseUploaded }
    val case_version = text(ProblemTableColumn.CASE_VERSION).bindTo { it.caseVersion }
    val is_in_group = boolean(ProblemTableColumn.IS_IN_GROUP).bindTo { it.isInGroup }
    val group_id = int(ProblemTableColumn.GROUP_ID).bindTo { it.groupId }
    val date_created = datetime(ProblemTableColumn.DATE_CREATED).bindTo { it.dateCreated }
    val date_last_modified = datetime(ProblemTableColumn.DATE_LAST_MODIFIED).bindTo { it.dateLastModified }

    val Database.problems get() = this.sequenceOf(Problems)
}

object ProblemTableColumn {
    const val ID = "id"
    const val PROBLEM_ID = "problem_id"
    const val TITLE = "title"
    const val AUTHOR = "author"
    const val CREATED_BY_USER = "created_by_user"
    const val LAST_MODIFIED_BY_USER = "last_modified_by_user"
    const val PROBLEM_TYPE = "problem_type"
    const val TIME_LIMIT = "time_limit"
    const val MEMORY_LIMIT = "memory_limit"
    const val STACK_LIMIT = "stack_limit"
    const val PROBLEM_DESCRIPTION = "problem_description"
    const val INPUT_DESCRIPTION = "input_description"
    const val OUTPUT_DESCRIPTION = "output_description"

    const val EXAMPLES = "examples"

    const val IS_REMOTE_JUDGE = "is_remote_judge"
    const val PROBLEM_SOURCE = "problem_source"
    const val DIFFICULTY = "difficulty"
    const val HINT = "hint"
    const val AUTHORITY = "authority"
    const val OI_SCORE = "oi_score"
    const val IS_CODE_SHARABLE = "is_code_sharable"
    const val JUDGE_MODE = "judge_mode"
    const val JUDGE_CASE_MODE = "judge_case_mode"
    const val SPJ_CODE = "spj_code"
    const val SPJ_LANGUAGE = "spj_language"
    const val REMOVE_END_BLANK_CHAR = "remove_end_blank_char"
    const val OPEN_CASE_RESULT = "open_case_result"
    const val IS_CASE_UPLOADED = "is_case_uploaded"
    const val CASE_VERSION = "case_version"
    const val IS_IN_GROUP = "is_in_group"
    const val GROUP_ID = "group_id"
    const val DATE_CREATED = "date_created"
    const val DATE_LAST_MODIFIED = "date_last_modified"
}
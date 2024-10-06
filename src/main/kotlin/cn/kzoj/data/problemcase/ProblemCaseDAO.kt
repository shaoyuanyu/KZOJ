package cn.kzoj.data.problemcase

import cn.kzoj.models.problemcase.ProblemCase
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProblemCaseDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<ProblemCaseDAO>(ProblemCases)

    var problemId       by ProblemCases.problemId
    var caseInFile      by ProblemCases.caseInFile
    var caseOutFile     by ProblemCases.caseOutFile
    var score           by ProblemCases.score
}

fun ProblemCaseDAO.expose(): ProblemCase =
    ProblemCase(
        problemId = this.problemId,
        caseInFile = this.caseInFile,
        caseOutFile = this.caseOutFile,
        score = this.score
    )
package cn.kzoj.persistence.database.problemcase

import cn.kzoj.dto.problemcase.ProblemCase
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProblemCaseEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProblemCaseEntity>(ProblemCaseTable)

    var problemId by ProblemCaseTable.problemId
    var caseInFile by ProblemCaseTable.caseInFile
    var caseOutFile by ProblemCaseTable.caseOutFile
    var score by ProblemCaseTable.score
}

fun ProblemCaseEntity.expose(): ProblemCase =
    ProblemCase(
        problemId = this.problemId,
        caseInFile = this.caseInFile,
        caseOutFile = this.caseOutFile,
        score = this.score
    )
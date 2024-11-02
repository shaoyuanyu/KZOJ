package cn.kzoj.dbConvertor.hojData.problemcase

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

@Suppress("unused")
class ProblemCaseEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<ProblemCaseEntity>(ProblemCaseTable)

    var pid             by ProblemCaseTable.pid
    var input           by ProblemCaseTable.input
    var output          by ProblemCaseTable.output
    var score           by ProblemCaseTable.score
    var status          by ProblemCaseTable.status
    var groupNum        by ProblemCaseTable.groupNum
    var gmtCreated      by ProblemCaseTable.gmtCreated
    var gmtModified     by ProblemCaseTable.gmtModified
}
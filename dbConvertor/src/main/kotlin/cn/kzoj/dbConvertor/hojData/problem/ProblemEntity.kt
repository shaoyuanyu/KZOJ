package cn.kzoj.dbConvertor.hojData.problem

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

@Suppress("Unused")
class ProblemEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<ProblemEntity>(ProblemTable)

    var problemId               by ProblemTable.problemId
    var title                   by ProblemTable.title
    var author                  by ProblemTable.author
    var problemType             by ProblemTable.problemType
    var timeLimit               by ProblemTable.timeLimit
    var memoryLimit             by ProblemTable.memoryLimit
    var stackLimit              by ProblemTable.stackLimit
    var problemDescription      by ProblemTable.problemDescription
    var inputDescription        by ProblemTable.inputDescription
    var outputDescription       by ProblemTable.outputDescription
    var examples                by ProblemTable.examples
    var isRemoteJudge           by ProblemTable.isRemoteJudge
    var problemSource           by ProblemTable.problemSource
    var difficulty              by ProblemTable.difficulty
    var hint                    by ProblemTable.hint
    var authority               by ProblemTable.authority
    var oiScore                 by ProblemTable.oiScore
    var isCodeSharable          by ProblemTable.isCodeSharable
    var judgeMode               by ProblemTable.judgeMode
    var judgeCaseMode           by ProblemTable.judgeCaseMode
    var spjCode                 by ProblemTable.spjCode
    var spjLanguage             by ProblemTable.spjLanguage
    var removeEndBlankChar      by ProblemTable.removeEndBlankChar
    var openCaseResult          by ProblemTable.openCaseResult
    var isCaseUploaded          by ProblemTable.isCaseUploaded
    var caseVersion             by ProblemTable.caseVersion
    var lastModifiedByUser      by ProblemTable.lastModifiedByUser
    var isInGroup               by ProblemTable.isInGroup
    var groupId                 by ProblemTable.groupId
    var dateCreated             by ProblemTable.dateCreated
    var dateLastModified        by ProblemTable.dateLastModified
}
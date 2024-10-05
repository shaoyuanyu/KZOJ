package cn.kzoj.data.problem

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProblemDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<ProblemDAO>(Problems)

    var problemId               by Problems.problemId
    var title                   by Problems.title
    var author                  by Problems.author
    var createdByUser           by Problems.createdByUser
    var description             by Problems.description
    var timeLimit               by Problems.timeLimit
    var memoryLimit             by Problems.memoryLimit
    var stackLimit              by Problems.stackLimit
    var inputDescription        by Problems.inputDescription
    var outputDescription       by Problems.outputDescription
    var examples                by Problems.examples
    var problemSource           by Problems.problemSource
    var difficulty              by Problems.difficulty
    var tip                     by Problems.tip
    var status                  by Problems.status
    var score                   by Problems.score
    var localTimeCreated        by Problems.localTimeCreated
    var localTimeLastModified   by Problems.localTimeLastModified
}

fun ProblemDAO.toProblemExposed(): ProblemExposed =
    ProblemExposed(
        problemId = this.problemId,
        title = this.title,
        author = this.author,
        createdByUser = this.createdByUser,
        description = this.description,
        timeLimit = this.timeLimit,
        memoryLimit = this.memoryLimit,
        stackLimit = this.stackLimit,
        inputDescription = this.inputDescription,
        outputDescription = this.outputDescription,
        examples = this.examples,
        problemSource = this.problemSource,
        difficulty = this.difficulty,
        tip = this.tip,
        status = this.status,
        score = this.score,
        localTimeCreated = localTimeCreated,
        localTimeLastModified = localTimeLastModified,
    )
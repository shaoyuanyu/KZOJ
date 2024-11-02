package cn.kzoj.persistence.database.problem

import cn.kzoj.dto.problem.Problem
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProblemEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProblemEntity>(ProblemTable)

    var title by ProblemTable.title
    var author by ProblemTable.author
    var createdByUser by ProblemTable.createdByUser
    var description by ProblemTable.description
    var timeLimit by ProblemTable.timeLimit
    var memoryLimit by ProblemTable.memoryLimit
    var stackLimit by ProblemTable.stackLimit
    var inputDescription by ProblemTable.inputDescription
    var outputDescription by ProblemTable.outputDescription
    var examples by ProblemTable.examples
    var problemSource by ProblemTable.problemSource
    var difficulty by ProblemTable.difficulty
    var tip by ProblemTable.tip
    var status by ProblemTable.status
    var score by ProblemTable.score
    var utcCreated by ProblemTable.utcCreated
    var utcUpdated by ProblemTable.utcUpdated
}

fun ProblemEntity.expose(): Problem =
    Problem(
        id = this.id.value,
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
        utcCreated = this.utcCreated,
        utcLastModified = this.utcUpdated,
    )

fun List<ProblemEntity>.expose(): List<Problem> =
    let {
        val problemArrayList: ArrayList<Problem> = arrayListOf()
        it.forEach { problemArrayList.add(it.expose()) }
        problemArrayList.toList()
    }
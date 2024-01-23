package cn.kzoj.data.problem

import cn.kzoj.data.problem.Problems.problems
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*

interface ProblemDAOI {
    suspend fun insert(problem: Problem)
    suspend fun update(problem: Problem)
    suspend fun delete(problem: Problem)
    fun getAllProblems(): List<Problem?>
    fun getProblemById(id: Int): Problem?
    fun getProblemByProblemId(problemId: String): Problem?
    fun getProblemsByTitle(title: String): List<Problem?>
}

class ProblemDAO(private val database: Database) : ProblemDAOI {
    override suspend fun insert(problem: Problem) {
        database.problems.add(problem)
    }

    override suspend fun update(problem: Problem) {
        database.problems.update(problem)
    }

    override suspend fun delete(problem: Problem) {
        problem.delete()
    }

    override fun getAllProblems(): List<Problem?> =
        database.problems.filter { it.id.isNotNull() }.toList()

    override fun getProblemById(id: Int): Problem? =
        database.problems.find { it.id eq id }

    override fun getProblemByProblemId(problemId: String): Problem? =
        database.problems.find { it.problem_id eq problemId }

    override fun getProblemsByTitle(title: String): List<Problem?> =
        database.problems.filter { it.title.like(title) }.toList()
}
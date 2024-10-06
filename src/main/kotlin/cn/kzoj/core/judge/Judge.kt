package cn.kzoj.core.judge

import cn.kzoj.common.JudgeStatus
import cn.kzoj.data.problemcase.ProblemCaseDAO
import cn.kzoj.data.problemcase.ProblemCases
import cn.kzoj.data.problemcase.expose
import cn.kzoj.models.judge.JudgeRequest
import cn.kzoj.models.submit.SubmitRequest
import cn.kzoj.models.judge.JudgeResult
import cn.kzoj.models.problemcase.ProblemCase
import cn.kzoj.models.submit.SubmitReceipt
import kotlinx.coroutines.*
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.io.File
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

@Suppress("OPT_IN_USAGE")
class Judge(
    private val goJudgeUrl: String,
    private val testCasePath: String,
    private val database: Database
) {
    // 待判队列
    private var judgeQueue: Queue<JudgeRequest> = LinkedList()

    // 判题结果列表(待查询的)
    private var judgeResultList: ArrayList<JudgeResult> = arrayListOf()

    // 可销毁的判题结果（包括已取出的或过期的）的id列表
    private var uselessJudgeResultIdList: ArrayList<String> = arrayListOf()

    init {
        GlobalScope.launch {
            worker()
        }
    }

    // 工作协程
    private suspend fun worker() {
        while (true) {
            if (judgeQueue.isEmpty()) {
                if (judgeResultList.isEmpty()) {
                    // 待判队列和判题结果列表均为空时，短暂挂起
                    delay(SLEEP_TIME_SECONDS)
                } else {
                    // 判题完成后超过设定时长，仍未查询结果的进行销毁
                    val currentTime = Clock.System.now()
                    val currentTimeZone = TimeZone.currentSystemDefault()
                    judgeResultList.forEach {
                        if (currentTime.periodUntil(it.judgeTime!!, currentTimeZone).minutes >= TIMEOUT_DURATION_MINUTES) {
                            uselessJudgeResultIdList.add(it.judgeId)
                        }
                    }
                }
            } else {
                val nextToJudge = judgeQueue.poll()
                judgeResultList.add(doJudge(nextToJudge))
            }

            // 销毁无用的判题结果
            while (uselessJudgeResultIdList.isNotEmpty()) {
                val uselessJudgeResultId = uselessJudgeResultIdList[0]
                uselessJudgeResultIdList.removeFirst()
                judgeResultList.remove(judgeResultList.find { it.judgeId == uselessJudgeResultId })
            }
        }
    }

    private suspend fun doJudge(judgeRequest: JudgeRequest): JudgeResult {
        val sandboxRun = SandboxRun(goJudgeUrl, judgeRequest)

        val judgeResult = JudgeResult(
            judgeId = judgeRequest.judgeId,
            status = JudgeStatus.Finished,
            accept = true,
            judgeTime = Clock.System.now()
        )

        if (!sandboxRun.compile()) {
            // 编译失败
            println("\n\ncompile failed\n\n")

            return judgeResult.also {
                it.accept = false
            }
        }

        judgeResult.evaluationPoint = arrayListOf()
        val problemCasePath = "$testCasePath/${judgeRequest.submitRequest.problemId}"
        val problemCaseList: List<ProblemCase> = getProblemCaseList(judgeRequest.submitRequest.problemId)

        problemCaseList.forEach {
            val caseContentPair = readProblemCaseContent(problemCasePath, it.caseInFile, it.caseOutFile)

            // run
            val testRes = sandboxRun.runTestCase(caseContentPair.first)

            if (testRes == caseContentPair.second) {
                judgeResult.evaluationPoint!!.add(true)
            } else {
                judgeResult.evaluationPoint!!.add(false)
                judgeResult.accept = false
            }
        }

        sandboxRun.delCache()

        return judgeResult
    }

    private suspend fun getProblemCaseList(problemId: Int): List<ProblemCase> =
        newSuspendedTransaction(context=Dispatchers.Default, db=database) {
            ProblemCaseDAO.find { ProblemCases.problemId eq problemId }.toList().let {
                val problemCaseArrayList: ArrayList<ProblemCase> = arrayListOf()
                it.forEach { problemCaseArrayList.add(it.expose()) }
                problemCaseArrayList.toList()
            }
        }

    private fun readProblemCaseContent(path: String, caseInFilename: String, caseOutFilename:String): Pair<String, String> =
        File(path, caseInFilename).readText() to File(path, caseOutFilename).readText()

    fun addJudgeRequest(submitRequest: SubmitRequest): SubmitReceipt {
        val submitTime = Clock.System.now()
        // 分配judgeId
        val judgeId = Base64.getUrlEncoder().encodeToString(
            MessageDigest.getInstance("SHA-256").digest(
                submitTime.toString().toByteArray()
            )
        )

        judgeQueue.add(JudgeRequest(submitRequest = submitRequest, submitTime = submitTime, judgeId = judgeId))

        val judgeQueueSize = judgeQueue.size
        return SubmitReceipt(
            judgeId = judgeId,
            status = if (judgeQueueSize>1) JudgeStatus.Queueing else JudgeStatus.Judging,
            positionInQueue = judgeQueueSize
        )
    }

    fun queryJudgeStatus(judgeId: String): SubmitReceipt =
        with (judgeQueue.find { it.judgeId == judgeId }) {
            if (this == null) {
                if (judgeResultList.find { it.judgeId == judgeId } == null) {
                    SubmitReceipt(judgeId = judgeId, status = JudgeStatus.NotFound, positionInQueue = 0)
                } else {
                    SubmitReceipt(judgeId = judgeId, status = JudgeStatus.Finished, positionInQueue = 0)
                }
            } else {
                SubmitReceipt(judgeId = judgeId, status = JudgeStatus.Queueing, positionInQueue = judgeQueue.indexOf(this))
            }
        }

    fun queryJudgeResult(judgeId: String): JudgeResult {
        val result = judgeResultList.find { it.judgeId == judgeId }

        if (result == null) {
            return JudgeResult(judgeId = judgeId, status = JudgeStatus.NotFound)
        } else {
            uselessJudgeResultIdList.add(judgeId)
            return result
        }
    }

    companion object {
        const val SLEEP_TIME_SECONDS: Long = 1000 // 休眠时间
        const val TIMEOUT_DURATION_MINUTES = 30 // 超时标准
    }
}
package cn.kzoj.core.judge

import cn.kzoj.common.JudgeStatus
import cn.kzoj.models.gojudge.*
import cn.kzoj.models.judge.JudgeRequest
import cn.kzoj.models.submit.SubmitRequest
import cn.kzoj.models.judge.JudgeResult
import cn.kzoj.models.submit.SubmitReceipt
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

@Suppress("OPT_IN_USAGE")
class Judge(
    private val goJudgeUrl: String
) {
    // 待判队列
    private var judgeQueue: Queue<JudgeRequest> = LinkedList()

    // 判题结果列表(待查询的)
    private var judgeResultList: ArrayList<JudgeResult> = arrayListOf()

    // 可销毁的判题结果（包括已取出的或过期的）的id列表
    private var uselessJudgeResultIdList: ArrayList<String> = arrayListOf()

    // 和GoJudge通信的Ktor客户端
    private val goJudgeClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

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
        val goJudgeRequest = if (judgeRequest.submitRequest.lang == "c") {
            GoJudgeRequest(
                cmd = listOf(
                    GoJudgeCommand(
                        args = listOf("/usr/bin/g++", "a.cc", "-o", "a"),
                        env = listOf("PATH=/usr/bin:/bin"),
                        files = listOf(
                            File.MemoryFile(content = ""),
                            File.Collector(name = "stdout", max = 10240),
                            File.Collector(name = "stderr", max = 10240),
                        ),
                        cpuLimit = 10000000000,
                        memoryLimit = 104857600,
                        procLimit = 50,
                        copyIn = mapOf(
                            "a.cc" to CopyIn.MemoryFile(content = judgeRequest.submitRequest.submittedCode),
                        ),
                        copyOut = listOf("stdout", "stderr"),
                        copyOutCached = listOf("a"),
                    )
                )
            )
        } else {
            GoJudgeRequestExample   // TODO: 其他语言待完善
        }

        val goJudgeResult = goJudgeClient.post("$goJudgeUrl/run") {
            contentType(ContentType.Application.Json)
            setBody(goJudgeRequest)
        }
        val goJudgeResultBody: GoJudgeResult = goJudgeResult.body<List<GoJudgeResult>>()[0]

        return JudgeResult(
            judgeId = judgeRequest.judgeId,
            status = JudgeStatus.Finished,
            accept = (goJudgeResultBody.status == Status.Accepted),
            judgeTime = Clock.System.now()
        )
    }

    suspend fun doJudgeTest() {
        val result = goJudgeClient.post("$goJudgeUrl/run") {
            contentType(ContentType.Application.Json)
            setBody(GoJudgeRequestExample)
        }
        val resultBody: List<GoJudgeResult> = result.body()

        if (resultBody[0].status == Status.Accepted) {
            println("\nAcc")
        }

        println("\ngoJudge result: $resultBody\n")
    }

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
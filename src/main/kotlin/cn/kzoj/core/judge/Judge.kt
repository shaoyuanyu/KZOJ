package cn.kzoj.core.judge

import cn.kzoj.common.JudgeStatus
import cn.kzoj.models.JudgeRequest
import cn.kzoj.models.SubmitRequest
import cn.kzoj.models.JudgeResult
import cn.kzoj.models.SubmitReceipt
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

@OptIn(DelicateCoroutinesApi::class)
class Judge {
    // 待判队列
    private var judgeQueue: Queue<JudgeRequest> = LinkedList()
    // 判题结果列表(待查询的)
    private var judgeResults: ArrayList<JudgeResult> = arrayListOf()

    init {
        GlobalScope.launch {
            worker()
        }
    }

    // 工作协程
    private suspend fun worker() {
        while (true) {
            if (judgeQueue.isEmpty()) {
                if (judgeResults.isEmpty()) {
                    // 待判队列和判题结果均为空时短暂挂起，减轻服务器负载
                    delay(SLEEP_TIME_SECONDS)
                } else {
                    val currentTime = Clock.System.now()
                    val currentTimeZone = TimeZone.currentSystemDefault()
                    judgeResults.forEach {
                        if (currentTime.periodUntil(it.judgeTime!!, currentTimeZone).minutes >= TIMEOUT_DURATION_MINUTES) {
                            judgeResults.remove(it)
                        }
                    }
                }
            } else {
                val nextToJudge = judgeQueue.poll()
                judgeResults.add(doJudge(nextToJudge))
            }
        }
    }

    private fun doJudge(judgeRequest: JudgeRequest): JudgeResult {
        // test code
        return JudgeResult(
            judgeId = judgeRequest.judgeId,
            status = JudgeStatus.FINISHED,
            accept = true,
            evaluationPoint = arrayListOf(true, true, true),
            judgeTime = Clock.System.now()
        )
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
            status = if (judgeQueueSize>1) JudgeStatus.QUEUEING else JudgeStatus.JUDGING,
            positionInQueue = judgeQueueSize
        )
    }

    fun queryJudgeStatus(judgeId: String): SubmitReceipt =
        with (judgeQueue.find { it.judgeId == judgeId }) {
            if (this == null) {
                SubmitReceipt(judgeId = judgeId, status = JudgeStatus.FINISHED, positionInQueue = 0)
            } else {
                SubmitReceipt(judgeId = judgeId, status = JudgeStatus.QUEUEING, positionInQueue = judgeQueue.indexOf(this))
            }
        }

    fun queryJudgeResult(judgeId: String): JudgeResult {
        val result = judgeResults.find { it.judgeId == judgeId }

        if (result == null) {
            return JudgeResult(judgeId = judgeId, status = JudgeStatus.QUEUEING)
        } else {
            judgeResults.remove(result)
            return result
        }
    }

    companion object {
        const val SLEEP_TIME_SECONDS: Long = 1000 // 休眠时间
        const val TIMEOUT_DURATION_MINUTES = 30 // 超时标准
    }
}
package cn.kzoj.core.judge

import cn.kzoj.data.problem.JudgeRequest
import cn.kzoj.data.problem.JudgeResult
import cn.kzoj.data.problem.JudgeStatus
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

@OptIn(DelicateCoroutinesApi::class)
class Judge {
    // 待判队列
    private var judgeQueue: Queue<JudgeRequest> = LinkedList()
    // 判题结果(待查询的)
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
                    delay(1000)
                } else {
                    val currentTime = LocalDateTime.now()
                    judgeResults.forEach {
                        if (Duration.between(it.judgeTime, currentTime) >= TIMEOUT_DURATION) {
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

    fun addJudgeRequest(judgeRequest: JudgeRequest): String {
        // 分配judgeId
        val judgeId = Base64.getUrlEncoder().encodeToString(
            MessageDigest.getInstance("SHA-256").digest(
                LocalDateTime.now().toString().toByteArray()
            )
        )

        judgeQueue.add(judgeRequest.copy(judgeId = judgeId))

        return judgeId
    }

    private fun doJudge(judgeRequest: JudgeRequest): JudgeResult {
        // test code
        return JudgeResult(
            judgeId = judgeRequest.judgeId!!,
            status = JudgeStatus.FINISHED,
            accept = true,
            evaluationPoint = arrayListOf(true, true, true),
            judgeTime = LocalDateTime.now()
        )
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
        val TIMEOUT_DURATION: Duration = Duration.ofMinutes(30)
    }
}
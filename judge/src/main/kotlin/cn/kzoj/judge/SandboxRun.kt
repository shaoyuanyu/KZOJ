package cn.kzoj.judge

import cn.kzoj.dto.gojudge.CopyIn
import cn.kzoj.dto.gojudge.File
import cn.kzoj.dto.gojudge.GoJudgeCommand
import cn.kzoj.dto.gojudge.GoJudgeRequest
import cn.kzoj.dto.gojudge.GoJudgeResult
import cn.kzoj.dto.gojudge.Status
import cn.kzoj.dto.judge.JudgeRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class SandboxRun(
    private val goJudgeUrl: String,
    private val judgeRequest: JudgeRequest
) {
    // 和GoJudge通信的Ktor客户端
    private val goJudgeClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    // GoJudge编译后缓存的可执行文件id，用于运行测试数据
    private var fileId: String? = null

    // 根据语言选择GoJudge参数
    private val languageConfig: LanguageConfig = when (judgeRequest.submitRequest.lang) {
        "c" -> SandboxConfig.c
        "cpp" -> SandboxConfig.cpp
        else -> SandboxConfig.unknown
    }
    private val compileArgs = languageConfig.compileArgs
    private val compileEnv = languageConfig.compileEnv
    private val srcName = languageConfig.srcName
    private val runArgs = languageConfig.runArgs
    private val runEnv = languageConfig.runEnv
    private val execName = languageConfig.execName

    /**
     * 编译，返回是否成功
     */
    suspend fun compile(): Boolean {
        val goJudgeRequest = GoJudgeRequest(
            cmd = listOf(
                GoJudgeCommand(
                    args = compileArgs,
                    env = compileEnv,
                    files = listOf(
                        File.MemoryFile(content = ""),
                        File.Collector(name = "stdout", max = 10240),
                        File.Collector(name = "stderr", max = 10240),
                    ),
                    cpuLimit = 10000000000,
                    memoryLimit = 104857600,
                    procLimit = 50,
                    copyIn = mapOf(
                        srcName to CopyIn.MemoryFile(content = judgeRequest.submitRequest.submittedCode),
                    ),
                    copyOut = listOf("stdout", "stderr"),
                    copyOutCached = listOf(execName),
                )
            )
        )

        val goJudgeResult = goJudgeClient.post("$goJudgeUrl/run") {
            contentType(ContentType.Application.Json)
            setBody(goJudgeRequest)
        }.body<List<GoJudgeResult>>()[0]

        fileId = goJudgeResult.fileIds?.get(execName)

        return (goJudgeResult.status == Status.Accepted)
    }

    /**
     * 运行测试数据，返回标准输出（Stdout）
     */
    suspend fun runTestCase(testCase: String): String? {
        if (fileId == null) {
            println("compile not completed, cannot run test case.")
            return null
        }

        val goJudgeRequest = GoJudgeRequest(
            cmd = listOf(
                GoJudgeCommand(
                    args = runArgs,
                    env = runEnv,
                    files = listOf(
                        File.MemoryFile(content = testCase),
                        File.Collector(name = "stdout", max = 10240),
                        File.Collector(name = "stderr", max = 10240),
                    ),
                    cpuLimit = 10000000000,
                    memoryLimit = 104857600,
                    procLimit = 50,
                    copyIn = mapOf(
                        execName to CopyIn.PreParedFile(fileId = fileId!!),
                    ),
                )
            )
        )

        val goJudgeResult = goJudgeClient.post("$goJudgeUrl/run") {
            contentType(ContentType.Application.Json)
            setBody(goJudgeRequest)
        }.body<List<GoJudgeResult>>()[0]

        return goJudgeResult.files?.get("stdout")
    }

    /**
     * 删除GoJudge缓存
     */
    suspend fun delCache() {
        goJudgeClient.delete("$goJudgeUrl/file/$fileId")
    }
}
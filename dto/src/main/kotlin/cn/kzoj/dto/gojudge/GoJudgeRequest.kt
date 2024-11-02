package cn.kzoj.dto.gojudge

import kotlinx.serialization.Serializable

@Serializable
data class GoJudgeRequest(
    val cmd: List<GoJudgeCommand>,
)

@Serializable
data class GoJudgeCommand(
    /**
     * 程序命令行参数
     */
    val args: List<String>,

    /**
     * 程序环境变量
     */
    val env: List<String>? = null,

    /**
     * 指定标准输入、标准输出和标准错误的文件
     */
    val files: List<File>? = null,

    /**
     * CPU时间限制，单位纳秒
     */
    val cpuLimit: Long? = null,

    /**
     * 内存限制，单位byte
     */
    val memoryLimit: Long? = null,

    /**
     * 栈内存限制，单位byte
     */
    val stackLimit: Int? = null,

    /**
     * 线程数量限制
     */
    val procLimit: Int? = null,

    /**
     * 在执行程序之前复制进容器的文件列表
     */
    val copyIn: Map<String, CopyIn>? = null,

    /**
     * 在执行程序后从容器文件系统中复制出来的文件列表
     *
     * 在文件名之后加入"?"来使文件变为可选，可选文件不存在的情况不会触发FileError
     */
    val copyOut: List<String>? = null,

    /**
     * 和copyOut相同，不过文件不返回内容，而是返回一个对应文件id
     *
     * 内容可以通过/file/:fileId接口下载
     */
    val copyOutCached: List<String>? = null,
)

@Suppress("unused")
@Serializable
sealed class File {
    @Serializable
    data class LocalFile(
        val src: String, // 文件绝对路径
    ): File()

    @Serializable
    data class MemoryFile(
        val content: String, // 文件内容
    ): File()

    @Serializable
    data class PreParedFile(
        val fileId: String, // 文件id
    ): File()

    @Serializable
    data class Collector(
        val name: String, // copyOut文件名
        val max: Int, // 最大大小限制
        val pipe: Boolean? = false, // 通过管道收集（默认为false，文件收集）
    ): File()

    @Serializable
    data class StreamIn(
        val streamIn: Boolean, // 流式输入
    ): File()

    @Serializable
    data class StreamOut(
        val streamOut: Boolean, // 流式输出
    ): File()
}

@Suppress("unused")
@Serializable
sealed class CopyIn {
    @Serializable
    data class LocalFile(
        val src: String, // 文件绝对路径
    ): CopyIn()

    @Serializable
    data class MemoryFile(
        val content: String, // 文件内容
    ): CopyIn()

    @Serializable
    data class PreParedFile(
        val fileId: String, // 文件id
    ): CopyIn()

    @Serializable
    data class Symlink(
        val symlink: String, // 符号连接目标
    ): CopyIn()
}

// 测试用
@Suppress("unused")
val GoJudgeRequestExample = GoJudgeRequest(
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
                "a.cc" to CopyIn.MemoryFile(content = "include <iostream>\nusing namespace std;\nint main() {\nint a, b;\ncin >> a >> b;\ncout << a + b << endl;\n}"),
            ),
            copyOut = listOf("stdout", "stderr"),
            copyOutCached = listOf("a"),
        )
    )
)
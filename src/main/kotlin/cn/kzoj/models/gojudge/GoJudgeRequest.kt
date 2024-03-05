package cn.kzoj.models.gojudge

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
    val env: List<String>?,

    /**
     * 指定标准输入、标准输出和标准错误的文件
     */
    val files: List<File>?,

    /**
     * CPU时间限制，单位纳秒
     */
    val cpuLimit: Long?,

    /**
     * 内存限制，单位byte
     */
    val memoryLimit: Long?,

    /**
     * 栈内存限制，单位byte
     */
    val stackLimit: Int? = null,

    /**
     * 线程数量限制
     */
    val procLimit: Int?,

    /**
     * 在执行程序之前复制进容器的文件列表
     */
    val copyIn: Map<String, CopyIn>?,

    /**
     * 在执行程序后从容器文件系统中复制出来的文件列表
     *
     * 在文件名之后加入"?"来使文件变为可选，可选文件不存在的情况不会触发FileError
     */
    val copyOut: List<String>?,

    /**
     * 和copyOut相同，不过文件不返回内容，而是返回一个对应文件id
     *
     * 内容可以通过/file/:fileId接口下载
     */
    val copyOutCached: List<String>?,
)

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

// 压力测试用
val GoJudgeRequestExample = GoJudgeRequest(
    cmd = listOf(
        GoJudgeCommand(
            args = listOf("/bin/cat", "a.hs"),
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
                "a.hs" to CopyIn.MemoryFile(content = "main = putStrLn \\\\\"Hello, World!\\\\"),
                "b" to CopyIn.MemoryFile(content = "TEST"),
            ),
            copyOut = listOf("stdout", "stderr"),
            copyOutCached = listOf("a"),
        ),
    )
)
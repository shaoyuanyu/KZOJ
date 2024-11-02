package cn.kzoj.dto.gojudge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoJudgeResult(
    /**
     * 状态
     */
    val status: Status,

    /**
     * 错误信息
     */
    val error: String? = null,

    /**
     * go-judge返回值
     */
    val exitStatus: Int,

    /**
     * CPU运行时间，单位纳秒
     */
    val time: Long,

    /**
     * 内存占用，单位byte
     */
    val memory: Long,

    /**
     * 物理运行时间，单位纳秒
     */
    val runTime: Long,

    /**
     * copyOut指定的文件内容
     */
    val files: Map<String, String>? = null,

    /**
     * copyFileCached指定的文件id
     */
    val fileIds: Map<String, String>? = null,

    /**
     * 文件错误详细信息
     */
    val fileError: List<FileError>? = null,
)

@Suppress("unused")
@Serializable
enum class Status {
    @SerialName("Accepted")                 Accepted,               // 正常情况
    @SerialName("Memory Limit Exceeded")    MemoryLimitExceeded,    // 内存超限
    @SerialName("Time Limit Exceeded")      TimeLimitExceeded,      // 时间超限
    @SerialName("Output Limit Exceeded")    OutputLimitExceeded,    // 输出超限
    @SerialName("File Error")               FileError,              // 文件错误
    @SerialName("Nonzero Exit Status")      NonzeroExitStatus,      // 非 0 退出值
    @SerialName("Signalled")                Signalled,              // 进程被信号终止
    @SerialName("Internal Error")           InternalError,          // 内部错误
}

@Serializable
data class FileError(
    /**
     * 错误文件名称
     */
    val name: String,

    /**
     * 错误代码
     */
    val type: FileErrorType,

    /**
     * 错误信息
     */
    val message: String? = null,
)

@Suppress("unused")
@Serializable
enum class FileErrorType {
    @SerialName("CopyInOpenFile")           CopyInOpenFile,
    @SerialName("CopyInCreateFile")         CopyInCreateFile,
    @SerialName("CopyInCopyContent")        CopyInCopyContent,
    @SerialName("CopyOutOpen")              CopyOutOpen,
    @SerialName("CopyOutNotRegularFile")    CopyOutNotRegularFile,
    @SerialName("CopyOutSizeExceeded")      CopyOutSizeExceeded,
    @SerialName("CopyOutCreateFile")        CopyOutCreateFile,
    @SerialName("CopyOutCopyContent")       CopyOutCopyContent,
    @SerialName("CollectSizeExceeded")      CollectSizeExceeded,
}
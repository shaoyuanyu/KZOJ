package cn.kzoj.models.gojudge

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
    val error: String?,

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
    val files: Map<String, String>?,

    /**
     * copyFileCached指定的文件id
     */
    val fileIds: Map<String, String>?,

    /**
     * 文件错误详细信息
     */
    val fileError: List<FileError>?,
)

enum class Status {
    Accepted, // 正常情况
    MemoryLimitExceeded, // 内存超限
    TimeLimitExceeded, // 时间超限
    OutputLimitExceeded, // 输出超限
    FileError, // 文件错误
    NonzeroExitStatus, // 非 0 退出值
    Signalled, // 进程被信号终止
    InternalError, // 内部错误
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
    val message: String?,
)

enum class FileErrorType {
    CopyInOpenFile,
    CopyInCreateFile,
    CopyInCopyContent,
    CopyOutOpen,
    CopyOutNotRegularFile,
    CopyOutSizeExceeded,
    CopyOutCreateFile,
    CopyOutCopyContent,
    CollectSizeExceeded,
}
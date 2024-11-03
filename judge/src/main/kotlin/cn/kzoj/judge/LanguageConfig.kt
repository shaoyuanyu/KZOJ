package cn.kzoj.judge

abstract class LanguageConfig {
    abstract val compileArgs: List<String>
    abstract val compileEnv: List<String>
    abstract val srcName: String

    abstract val runArgs: List<String>
    abstract val runEnv: List<String>
    abstract val execName: String
}

class CConfig : LanguageConfig() {
    override val compileArgs: List<String> = listOf("/usr/bin/gcc", "a.c", "-o", "a")
    override val compileEnv: List<String> = listOf("PATH=/usr/bin:/bin")
    override val srcName: String = "a.c"

    override val runArgs: List<String> = listOf("a")
    override val runEnv: List<String> = listOf("PATH=/usr/bin:/bin")
    override val execName: String = "a"
}

class CppConfig : LanguageConfig() {
    override val compileArgs: List<String> = listOf("/usr/bin/g++", "a.cc", "-o", "a")
    override val compileEnv: List<String> = listOf("PATH=/usr/bin:/bin")
    override val srcName: String = "a.cc"

    override val runArgs: List<String> = listOf("a")
    override val runEnv: List<String> = listOf("PATH=/usr/bin:/bin")
    override val execName: String = "a"
}

class UnknownConfig : LanguageConfig() {
    override val compileArgs: List<String> = listOf()
    override val compileEnv: List<String> = listOf()
    override val srcName: String = ""

    override val runArgs: List<String> = listOf()
    override val runEnv: List<String> = listOf()
    override val execName: String = ""
}
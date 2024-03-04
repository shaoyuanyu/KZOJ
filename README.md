# KZOJ
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-informational)](https://kotlinlang.org/)
[![Ktor](https://img.shields.io/badge/Ktor-2.3-informational)](https://ktor.io/)
[![Ktorm](https://img.shields.io/badge/Ktorm-3.6-informational)](https://www.ktorm.org/)
[![go-judge](https://img.shields.io/badge/goJudge-1.8.2-informational)](https://github.com/criyle/go-judge/)

本项目为KZOJ的后端，采用Kotlin语言的Ktor框架，判题模块来自开源项目[go-judge](https://github.com/criyle/go-judge)。

前端部分请移步[CSOJ-frontend](https://github.com/shaoyuanyu/CSOJ-frontend)。

## 环境配置

### 1. 安装MySQL并启动

### 2. 配置环境变量
```
DB_URL = jdbc:mysql://localhost:MySQL端口/数据库名称;
DB_USER = MySQL用户名;
DB_PASSWORD = MySQL密码;
```

### 3. 配置前端
在项目根目录下创建 `vue-app` 文件夹，将静态编译的vue前端放入其中。

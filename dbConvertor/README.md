# cn.kzoj.dbConvertor

dbConvertor模块用于从hoj数据库迁移到kzoj数据库

## 运行

首先配置以下环境变量：

```
HOJ_URL
HOJ_USER
HOJ_PASSWORD

KZOJ_URL
KZOJ_USER
KZOJ_PASSWORD

MINIO_URL
MINIO_USER
MINIO_PASSWORD

HOJ_PROBLEM_CASE_PATH
```

在 docker 容器中进入程序所在目录，运行本模块主类

```shell
$ cd /app
$ java -cp kzoj.jar cn.kzoj.dbConvertor.MainKt
```
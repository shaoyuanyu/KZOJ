# KZOJ

## 开发环境配置

### 配置环境变量
```
HOST                        (可选)
PORT                        (可选)
SESSION_STORAGE_PATH        (可选)
DB_URL
DB_DRIVER                   (可选)
DB_USER
DB_PASSWORD
MINIO_URL
MINIO_USER
MINIO_PASSWORD
GOJUDGE_URL                 (可选)
```

## 生产环境配置

### 配置 docker-compose.yml
```
services:
    web:
        ...
        environment:
            HOST: "0.0.0.0"
            DB_URL: "jdbc:mysql://host.docker.internal:{MySQL映射端口}/{数据库名称}"
            DB_USER: "{MySQL用户名}"
            DB_PASSWORD: "{MySQL密码}"
            MINIO_URL: "http://host.docker.internal:{MinIO API映射端口}"
            MINIO_USER: "{MinIO用户名}"
            MINIO_PASSWORD: "{MinIO密码}"
            GOJUDGE_URL: "http://host.docker.internal:{GoJudge映射端口}"
        ...
    db:
        ...
        environment:
            MYSQL_ROOT_PASSWORD: "MySQL密码"
        ports:
            - "{MySQL映射端口}:3306"
        ...
    minio:
        ...
        environment:
            MINIO_ROOT_USER: "{MinIO用户名}"
            MINIO_ROOT_PASSWORD: "{MinIO密码}"
        ports:
            - "{MinIO API映射端口}:9000"
            - "{MinIO 后台映射端口}:9001"
        ...
    sandbox:
        ...
        ports:
            - "{GoJudge映射端口}:5050"
```

### 运行容器
```
docker compose -p kzoj up -d
```
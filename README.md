# KZOJ

## 开发环境配置

首先安装 Docker 和 Docker compose。

### 配置环境变量（可选）

默认情况下，go-judge 使用 5050 端口，mysql 使用 3306 端口，minio 使用 9000 和 9001 端口。

如果需要修改容器映射的端口号，拷贝一份`.env.default`文件，并修改对应的端口：

```shell
$ cp .env.default .env
```

注意：
1. 无论怎么修改端口，都不要修改`.env.docker`中的地址；
2. 对于本地开发，可以在`Idea`的`Run/Debug Configuration`中设置环境变量，也可根据`.env`更新环境变量（使用容器运行则无需考虑此步骤）：
   ```shell
   $ export $(grep -v '^#' .env | xargs)
   ```

### 配置网络代理（可选）

如果 docker 构建镜像卡住，修改`/etc/systemd/system/docker.service.d/http-proxy.conf`后重启 docker engine：

```shell
$ cat > /etc/systemd/system/docker.service.d/http-proxy.conf << EOF
[Service]
Environment="HTTP_PROXY=http://127.0.0.1:7890"
Environment="HTTPS_PROXY=http://127.0.0.1:7890"
EOF
$ systemctl daemon-reload
$ systemctl restart docker
```

如果 gradle 下载依赖卡住，修改`gradle.properties`，添加以下内容：

```conf
systemProp.http.proxyHost=127.0.0.1
systemProp.http.proxyPort=7890
systemProp.https.proxyHost=127.0.0.1
systemProp.https.proxyPort=7890
```

### 启动相关服务容器

```shell
$ docker compose up -d sandbox mysql minio
```

### 启动网页端容器

```shell
$ docker compose up web
```

### 启动服务端程序

本地运行：

```shell
$ ./gradlew run
```

容器运行：

```shell
$ docker compose up app
```

容器编译并运行：

```shell
$ docker compose up --build app
```

## 生产环境配置

1. 拷贝`.env`文件，设置复杂的用户名和密码。
2. 修改`docker-compose.yml`：
   - 关闭服务容器的端口转发，只留 app 和 web 两个容器的端口。
   - 修改 volume 配置，持久化所有数据。
3. 启动所有服务：`docker compose up -d`。

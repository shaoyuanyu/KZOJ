# Stage 1: Cache Gradle dependencies
FROM gradle:latest AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME=/home/gradle/cache_home

# 拷贝各模块gradle配置
COPY build.gradle.kts gradle.properties settings.gradle.kts /home/gradle/app/
COPY gradle/libs.versions.toml /home/gradle/app/gradle/
COPY app/build.gradle.kts /home/gradle/app/app/
COPY dbConvertor/build.gradle.kts /home/gradle/app/dbConvertor/
COPY dto/build.gradle.kts /home/gradle/app/dto/
COPY judge/build.gradle.kts /home/gradle/app/judge/
COPY persistence/build.gradle.kts /home/gradle/app/persistence/

WORKDIR /home/gradle/app
RUN gradle clean build -i --stacktrace

# Stage 2: Build Application
FROM gradle:latest AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/app/
WORKDIR /usr/src/app
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Build the fat JAR, Gradle also supports shadow
# and boot JAR by default.
RUN gradle buildFatJar --no-daemon

# Stage 3: Create the Runtime Image
FROM amazoncorretto:21 AS runtime
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/app/build/libs/*.jar /app/kzoj.jar
ENTRYPOINT ["java","-jar","/app/kzoj.jar"]
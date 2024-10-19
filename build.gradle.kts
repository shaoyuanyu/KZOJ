val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val mysqlConnectorVersion: String by project
val hikaricpVersion: String by project
val minioVersion: String by project
val datetimeVersion: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "3.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
}

group = "cn.kzoj"
version = "0.0.2"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    fatJar {
        archiveFileName.set("kzoj.fat.jar")
    }
    docker {
        jreVersion.set(JavaVersion.VERSION_21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // ktor-server
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-cors")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-status-pages")
    testImplementation("io.ktor:ktor-server-tests-host")

    // ktor client
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-apache")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")

    // exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-json:$exposedVersion")
    // mysql-connector-j
    implementation("com.mysql:mysql-connector-j:$mysqlConnectorVersion")
    // HiKariCP
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")

    // minio
    implementation("io.minio:minio:$minioVersion")

    // logback
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    // kotlin test junit
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val mysqlConnectorVersion: String by project
val hikaricpVersion: String by project
val datetimeVersion: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.12"
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
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-resources-jvm")
    implementation("io.ktor:ktor-server-status-pages")
    testImplementation("io.ktor:ktor-server-tests-jvm")

    // ktor client
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-client-apache-jvm")
    implementation("io.ktor:ktor-client-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")

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

    // logback
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    // kotlin test junit
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

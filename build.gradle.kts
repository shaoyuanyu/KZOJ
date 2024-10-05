val kotlinVersion: String by project
val logbackVersion: String by project
val ktormVersion: String by project
val mysqlConnectorVersion: String by project
val datetimeVersion: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.7"
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
    // ktor server
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-auth-jvm") // auth
    implementation("io.ktor:ktor-server-auth-jwt-jvm") // auth jwt
    implementation("io.ktor:ktor-server-content-negotiation-jvm") // content negotiation
    implementation("io.ktor:ktor-server-netty-jvm") // netty
    implementation("io.ktor:ktor-server-cors-jvm") // cors
    implementation("io.ktor:ktor-server-resources-jvm") // resources
    testImplementation("io.ktor:ktor-server-tests-jvm") // tests

    // ktor client
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-client-apache-jvm")
    implementation("io.ktor:ktor-client-content-negotiation-jvm")

    // ktor
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm") // serialization

    // ktorm
    implementation("org.ktorm:ktorm-core:$ktormVersion")

    // mysql-connector-j
    implementation("com.mysql:mysql-connector-j:$mysqlConnectorVersion")

    // logback
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    // kotlin test junit
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

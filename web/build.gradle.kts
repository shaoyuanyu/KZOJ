plugins {
    kotlin("jvm") version libs.versions.kotlin
    id("io.ktor.plugin") version libs.versions.ktor
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin
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
        jreVersion.set(JavaVersion.VERSION_18)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":api"))
    implementation(project(":persistence"))
    implementation(project(":judge"))

    implementation(libs.bundles.ktor.server)
    implementation(libs.exposed.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.logback.classic)
    implementation(libs.minio)
}

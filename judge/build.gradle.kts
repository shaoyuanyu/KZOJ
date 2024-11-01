plugins {
    kotlin("jvm") version libs.versions.kotlin
}

group = "cn.kzoj"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":dto"))
    implementation(project(":persistence"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.ktor.client)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.logback.classic)
}
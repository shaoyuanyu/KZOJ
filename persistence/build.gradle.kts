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

    implementation(libs.bundles.exposed)
    implementation(libs.hikaricp)
    implementation(libs.kotlinx.datetime)
    implementation(libs.logback.classic)
    implementation(libs.mysql.connector)
    implementation(libs.minio)
}
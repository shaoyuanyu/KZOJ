plugins {
    kotlin("jvm") version libs.versions.kotlin
}

group = "cn.kzoj"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    dependencies {
        implementation(project(":dto"))
        implementation(project(":persistence"))

        implementation(libs.bundles.exposed)
        implementation(libs.hikaricp)
        implementation(libs.kotlinx.datetime)
        implementation(libs.logback.classic)
        implementation(libs.minio)
        implementation(libs.mysql.connector)
    }
}
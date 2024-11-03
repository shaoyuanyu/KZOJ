plugins {
    id("buildlogic.kotlin-library-conventions")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

group = "cn.kzoj"
version = "0.0.1"

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
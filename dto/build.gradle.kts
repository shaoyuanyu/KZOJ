plugins {
    id("buildlogic.kotlin-library-conventions")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

group = "cn.kzoj"
version = "1.0.0"

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
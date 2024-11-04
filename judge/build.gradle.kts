plugins {
    id("buildlogic.kotlin-library-conventions")
}

group = "cn.kzoj"
version = "1.0.0"

dependencies {
    implementation(project(":dto"))
    implementation(project(":persistence"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.ktor.client)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.logback.classic)
}
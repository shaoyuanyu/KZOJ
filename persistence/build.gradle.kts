plugins {
    id("buildlogic.kotlin-library-conventions")
}

group = "cn.kzoj"
version = "1.0.0"

dependencies {
    implementation(project(":dto"))

    implementation(libs.bundles.exposed)
    implementation(libs.hikaricp)
    implementation(libs.jbcrypt)
    implementation(libs.kotlinx.datetime)
    implementation(libs.logback.classic)
    implementation(libs.mysql.connector)
    implementation(libs.minio)
}
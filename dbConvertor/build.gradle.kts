plugins {
    id("buildlogic.kotlin-application-conventions")
}

group = "cn.kzoj"
version = "0.0.1"

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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "KZOJ"

include("app", "dto", "judge", "persistence", "dbConvertor")

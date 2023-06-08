plugins {
    kotlin("jvm")
    id("com.github.rodm.teamcity-server")
}

dependencies {
    compile(kotlin("stdlib"))

    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.+")
}

teamcity {
    version = "2023.05"

    server {
        descriptor = file("teamcity-plugin.xml")
        tokens = mapOf("Version" to rootProject.version)
        archiveName = "support-plugin.zip"
    }
}
import Dependencies.Ktor
import Dependencies.Logging
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation(project(":api-models"))

    implementation(Ktor.serverNetty)
    implementation(Ktor.serverCore)
    implementation(Ktor.serverHostCommon)

    implementation(Ktor.auth)
    implementation(Ktor.authJwt)
    implementation(Ktor.htmlBuilder)
    implementation(Ktor.kotlinxSerialization)

    implementation(Logging.logback)

    testImplementation(Ktor.serverTestHost)
}

tasks {

    withType<KotlinCompile>().all {
        kotlinOptions.freeCompilerArgs += "-Xopt-in=io.ktor.util.KtorExperimentalAPI"
    }
}

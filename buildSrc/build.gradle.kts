plugins {
    `kotlin-dsl`
    id("com.github.ben-manes.versions") version "0.28.0"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

val versions_kotlin: String by project
dependencies {
    implementation(kotlin("gradle-plugin", version = versions_kotlin))
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

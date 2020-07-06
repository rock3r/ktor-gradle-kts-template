import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }

    // Force ktlint version used by kotlinter plugin
    configurations.classpath.get()
            .resolutionStrategy.force("com.github.pinterest:ktlint:${DependencyVersions.ktlint}")
}

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.3.72" apply false

    id("com.github.ben-manes.versions") version "0.28.0"

    id("io.gitlab.arturbosch.detekt") version "1.10.0"
    id("org.jmailen.kotlinter") version "2.4.1"
}

group = "dev.sebastiano"
version = "0.0.1-SNAPSHOT"

subprojects {
    group = rootProject.group
    version = rootProject.version

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        maven(url = "https://dl.bintray.com/kotlin/kotlinx")
        mavenCentral()
        jcenter()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation(Dependencies.Kotlinx.serialization)

        testImplementation(Dependencies.JUnit.jupiterApi)
        testImplementation(Dependencies.JUnit.jupiterParams)
        testRuntimeOnly(Dependencies.JUnit.jupiterEngine)
    }

    configurations.forEach { config ->
        config.resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-")) {
                useVersion(DependencyVersions.kotlin)
            }
        }
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-progressive")
            }
        }

        named<Test>("test") {
            useJUnitPlatform()
        }
    }
}

val installPrePushHook by tasks.registering(Copy::class) {
    from(buildConfigFile("hooks"))
    destinationDir = File(rootDir, ".git/hooks")

    doLast {
        logger.info("Installed pre-push hook")
    }
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jmailen.kotlinter")

    detekt {
        toolVersion = DependencyVersions.detekt
        autoCorrect = !isCi()
        input = files("src/main/java", "src/main/kotlin", "buildSrc/src/main/kotlin")
        config = files(buildConfigFile("detekt/detekt.yml"))
        buildUponDefaultConfig = true
    }

    kotlinter {
        indentSize = 4
        reporters = arrayOf("html", "checkstyle", "plain")
    }

    tasks {
        compileKotlin {
            dependsOn(installPrePushHook)
        }
    }
}

tasks {
    wrapper {
        distributionType = Wrapper.DistributionType.ALL
    }

    @Suppress("UNUSED_VARIABLE") // Used by Gradle
    val prePushCheck by registering {
        dependsOn(lintKotlin)
    }
}

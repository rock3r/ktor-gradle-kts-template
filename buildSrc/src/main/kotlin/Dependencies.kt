object Dependencies {

    object JUnit {
        const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${DependencyVersions.jUnit}"
        const val jupiterParams = "org.junit.jupiter:junit-jupiter-params:${DependencyVersions.jUnit}"
        const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${DependencyVersions.jUnit}"
    }

    object Kotlinx {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${DependencyVersions.kotlinxSerialization}"

    }

    object Ktor {
        const val serverNetty = "io.ktor:ktor-server-netty:${DependencyVersions.ktor}"
        const val serverCore = "io.ktor:ktor-server-core:${DependencyVersions.ktor}"
        const val serverHostCommon = "io.ktor:ktor-server-host-common:${DependencyVersions.ktor}"
        const val serverTestHost = "io.ktor:ktor-server-test-host:${DependencyVersions.ktor}"

        const val auth = "io.ktor:ktor-auth:${DependencyVersions.ktor}"
        const val authJwt = "io.ktor:ktor-auth-jwt:${DependencyVersions.ktor}"
        const val kotlinxSerialization = "io.ktor:ktor-serialization:${DependencyVersions.ktor}"
        const val htmlBuilder = "io.ktor:ktor-html-builder:${DependencyVersions.ktor}"
    }

    object Logging {
        const val logback = "ch.qos.logback:logback-classic:${DependencyVersions.logback}"
    }
}

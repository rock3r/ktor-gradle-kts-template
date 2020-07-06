@file:JvmName("ServiceMain")

package dev.sebastiano.example

import io.ktor.application.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import dev.sebastiano.example.config.Configuration
import dev.sebastiano.example.config.Environment
import dev.sebastiano.example.setup.setupAuthentication
import dev.sebastiano.example.setup.setupCompression
import dev.sebastiano.example.setup.setupContentNegotiation
import dev.sebastiano.example.setup.setupCors
import dev.sebastiano.example.setup.setupDefaultHeaders
import dev.sebastiano.example.setup.setupLogging
import dev.sebastiano.example.setup.setupRouting
import dev.sebastiano.example.setup.setupWebsocket

private val logger: Logger = LoggerFactory.getLogger("KtorApplicationMain")

private val Application.envKind
    get() = environment.config.property("ktor.environment").getString()

internal val Application.isDevMode
    get() = envKind == "dev"

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
internal fun Application.module(devModeOverride: Boolean? = null) {
    if (devModeOverride != null) {
        logger.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        logger.warn("!!! devModeOverride is set to $devModeOverride, overriding the detected env of $envKind !!!")
        logger.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    }

    val developmentMode = devModeOverride ?: isDevMode
    val configuration = currentEnvironment.config
    logger.info("Web service version ${configuration.version} starting up...")
    logger.info("Environment: ${currentEnvironment.name}, devMode: $developmentMode")

    initDatabase(configuration)

    setupAuthentication(developmentMode)
    setupCompression()
    setupContentNegotiation()
    setupLogging()
    setupCors()
    setupDefaultHeaders(configuration)
    setupRouting(developmentMode)
}

internal val Application.currentEnvironment: Environment
    get() = Environment.current(this)

fun initDatabase(configuration: Configuration) {
    // TODO
}

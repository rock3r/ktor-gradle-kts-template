package dev.sebastiano.example.setup

import dev.sebastiano.example.AuthenticationException
import dev.sebastiano.example.AuthorizationException
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.authenticate
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ws.hook.models.rest.ApiEmployee
import ws.hook.models.rest.ApiErrorResponse

internal fun Application.setupRouting(developmentMode: Boolean) {
    val logger = LoggerFactory.getLogger("KtorRouting")

    routing {
        trace { logger.debug("Routing ${it.call.request.uri}...\n\n${it.buildText()}") }

        setupStatusPages(developmentMode, logger)

        get("/health") {
            call.respondText("OK", contentType = ContentType.Text.Plain.withCharsetUtf8())
        }

        authenticate("google-auth") {
            get("/employee/me") {
                call.respond(HttpStatusCode.OK, ApiEmployee("0", "Test Employee", "Employee"))
            }
        }

        get {
            call.respondError("Not found", HttpStatusCode.NotFound, developmentMode)
        }
    }
}

private fun Routing.setupStatusPages(testing: Boolean, logger: Logger) {
    install(StatusPages) {
        exception<AuthenticationException> { cause ->
            call.respondError("Authentication failed", HttpStatusCode.Unauthorized, testing, cause)
        }
        exception<AuthorizationException> { cause ->
            val uri = call.request.uri
            call.respondError("You don't have permissions to access $uri", HttpStatusCode.Forbidden, testing, cause)
        }

        exception<Throwable> { cause ->
            val uri = call.request.uri
            logger.warn("Unhandled exception: ${cause.message}", cause)
            call.respondError("Error while accessing $uri. ${cause.message}", HttpStatusCode.InternalServerError, testing, cause)
        }
    }
}

internal suspend fun ApplicationCall.respondError(
    message: String,
    statusCode: HttpStatusCode,
    developmentMode: Boolean,
    throwable: Throwable? = null
) {
    val stackTraceIfTesting = if (developmentMode) {
        throwable?.stackTraceEntries()?.joinToString("\n")
    } else {
        null
    }

    val error = ApiErrorResponse.with(message, stackTraceIfTesting)
    respond(statusCode, error)
}

private fun Throwable.stackTraceEntries() = this.stackTrace.iterator().asSequence().toList()

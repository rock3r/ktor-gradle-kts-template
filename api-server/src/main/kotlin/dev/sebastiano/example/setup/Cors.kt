package dev.sebastiano.example.setup

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import java.util.concurrent.TimeUnit

internal fun Application.setupCors() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.Authorization)

        anyHost()
        allowCredentials = true

        @Suppress("MagicNumber") // The '5' is pretty clear in its meaning here
        maxAgeInSeconds = TimeUnit.MINUTES.toSeconds(5)
    }
}

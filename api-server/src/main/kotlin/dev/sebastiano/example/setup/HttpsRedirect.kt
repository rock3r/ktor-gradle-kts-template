package dev.sebastiano.example.setup

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.HttpsRedirect

internal fun Application.setupHttpsRedirect(developmentMode: Boolean) {
    if (developmentMode) return

    install(HttpsRedirect) {
        @Suppress("MagicNumber") // The port number
        sslPort = 443
        permanentRedirect = true
    }
}

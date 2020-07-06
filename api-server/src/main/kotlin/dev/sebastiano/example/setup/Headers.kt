package dev.sebastiano.example.setup

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpHeaders
import dev.sebastiano.example.config.Configuration

internal fun Application.setupDefaultHeaders(configuration: Configuration) {
    install(DefaultHeaders) {
        header(HttpHeaders.Server, "MyServer-v${configuration.version}")
        header("X-Engine", "Ktor")
    }
}

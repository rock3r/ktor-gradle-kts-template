package dev.sebastiano.example.setup

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.gzip

internal fun Application.setupCompression() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
    }
}

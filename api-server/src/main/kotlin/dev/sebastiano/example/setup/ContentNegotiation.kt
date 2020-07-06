package dev.sebastiano.example.setup

import dev.sebastiano.example.converters.HtmlConverter
import dev.sebastiano.example.converters.PlainTextConverter
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.json
import io.ktor.serialization.serialization
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import ws.hook.jsonConfiguration

internal fun Application.setupContentNegotiation() {
    install(ContentNegotiation) {
        json(json = Json(jsonConfiguration))
        register(ContentType.Text.Plain, PlainTextConverter())
        register(ContentType.Text.Html, HtmlConverter())
    }
}

package dev.sebastiano.example.setup

import io.ktor.http.ContentType
import io.ktor.http.withCharset

fun ContentType.withCharsetUtf8(): ContentType = withCharset(Charsets.UTF_8)

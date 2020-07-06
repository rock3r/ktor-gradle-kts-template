package dev.sebastiano.example.setup

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.auth.parseAuthorizationHeader
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.parseAuthorizationHeader

internal fun Application.setupAuthentication(developmentMode: Boolean) {
    install(Authentication) {
        val jwtAudience = environment.config.property("jwt.audience").getString()
        val jwtRealm = environment.config.property("jwt.realm").getString()

        jwt("google-auth") {
            realm = jwtRealm
            verifier(GoogleTokenVerifier(jwtAudience))
            validate { credential ->
                // The GoogleTokenVerifier already verified everything is a-ok with the token,
                // this is more pro-forma than anything else
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
            challenge { _, _ ->
                call.respondError("Unauthorized", HttpStatusCode.Unauthorized, developmentMode)
            }
            authHeader { call ->
                try {
                    call.request.parseAuthorizationHeader() ?: parseAuthorizationHeader("Bearer ${call.parameters["token"]}")
                } catch (ex: IllegalArgumentException) {
                    null
                }
            }
        }
    }
}

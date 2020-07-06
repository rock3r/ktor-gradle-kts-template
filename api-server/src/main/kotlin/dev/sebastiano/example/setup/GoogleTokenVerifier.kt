package dev.sebastiano.example.setup

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import java.io.IOException
import java.security.GeneralSecurityException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("SameParameterValue")
internal class GoogleTokenVerifier(audience: String) : JWTVerifier {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val verifier = GoogleIdTokenVerifier.Builder(
        NetHttpTransport(),
        JacksonFactory()
    )
        .setAudience(listOf(audience))
        .build()

    override fun verify(tokenString: String): DecodedJWT {
        val idToken = tryValidating(tokenString)
        if (idToken == null) {
            logger.warn("Token not valid — failed Google validation")
            throw JWTVerificationException("Google JWT didn't pass validation")
        }

        return GoogleIdTokenDecodedJWT(idToken, tokenString)
    }

    private fun tryValidating(token: String?): GoogleIdToken? = try {
        verifier.verify(token)
    } catch (e: IOException) {
        throw JWTVerificationException("Unable to parse token", e)
    } catch (e: GeneralSecurityException) {
        throw JWTVerificationException("Unable to parse token", e)
    }

    @Deprecated(
        "Use verify(String) instead",
        level = DeprecationLevel.ERROR,
        replaceWith = ReplaceWith("verify(java.lang.String)", "dev.sebastiano.example.setup.GoogleTokenVerifier")
    )
    override fun verify(jwt: DecodedJWT?): DecodedJWT {
        throw UnsupportedOperationException("Use verify(String) instead")
    }
}

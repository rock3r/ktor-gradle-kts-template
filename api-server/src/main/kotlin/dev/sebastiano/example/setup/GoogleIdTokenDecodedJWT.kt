package dev.sebastiano.example.setup

import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import java.util.Date

internal data class GoogleIdTokenDecodedJWT(private val idToken: GoogleIdToken, private val tokenString: String) : DecodedJWT {

    override fun getAlgorithm() = "RS256"

    override fun getExpiresAt(): Date =
        Date(idToken.payload.expirationTimeSeconds * MILLIS_IN_A_SECOND)

    override fun getAudience(): MutableList<String> = idToken.payload.audienceAsList

    override fun getId(): String = idToken.payload.jwtId

    override fun getType(): String = idToken.payload.type

    override fun getSignature(): String = String(idToken.signatureBytes)

    override fun getKeyId(): String = idToken.header.keyId

    override fun getHeader(): String {
        val firstDot = tokenString.indexOf('.')
        return tokenString.substring(0, firstDot)
    }

    override fun getToken(): String = tokenString

    override fun getContentType(): String = TODO()

    override fun getNotBefore(): Date =
        Date(idToken.payload.notBeforeTimeSeconds * MILLIS_IN_A_SECOND)

    override fun getSubject(): String = idToken.payload.subject

    override fun getPayload(): String {
        val firstDot = tokenString.indexOf('.')
        val secondDot: Int = tokenString.indexOf('.', firstDot + 1)
        return tokenString.substring(firstDot + 1, secondDot)
    }

    override fun getIssuer(): String = idToken.payload.issuer

    override fun getClaims(): MutableMap<String, Claim> = TODO()

    override fun getIssuedAt(): Date =
        Date(idToken.payload.issuedAtTimeSeconds * MILLIS_IN_A_SECOND)

    override fun getClaim(name: String?): Claim = TODO()

    override fun getHeaderClaim(name: String?): Claim = TODO()

    companion object {
        private const val MILLIS_IN_A_SECOND = 1000
    }
}

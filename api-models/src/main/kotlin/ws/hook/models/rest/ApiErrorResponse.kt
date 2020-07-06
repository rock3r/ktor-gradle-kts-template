package ws.hook.models.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    @SerialName("error") val error: Error
) {

    @Serializable
    data class Error(
        @SerialName("message") val message: String,
        @SerialName("stack_trace") val stackTrace: String? = null
    )

    companion object {

        fun with(message: String, stackTrace: String? = null) = ApiErrorResponse(
            Error(
                message,
                stackTrace
            )
        )
    }
}

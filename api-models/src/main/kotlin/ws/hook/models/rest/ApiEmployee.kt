package ws.hook.models.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiEmployee(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("job_description") val jobDescription: String? = null
)

package ws.hook

import kotlinx.serialization.json.JsonConfiguration

val jsonConfiguration = JsonConfiguration.Stable.copy(
    ignoreUnknownKeys = true,
    prettyPrint = true,
    indent = "  ",
    encodeDefaults = false
)

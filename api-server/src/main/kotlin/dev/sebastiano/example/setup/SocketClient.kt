package dev.sebastiano.example.setup

import io.ktor.http.cio.websocket.DefaultWebSocketSession
import java.util.concurrent.atomic.AtomicInteger

class SocketClient(val session: DefaultWebSocketSession, val channelId: String) {
    companion object {
        var lastId = AtomicInteger(0)
    }

    val id = lastId.getAndIncrement()
    val name = "user$id"
}

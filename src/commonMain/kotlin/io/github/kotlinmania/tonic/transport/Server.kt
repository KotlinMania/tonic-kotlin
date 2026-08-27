// port-lint: source tonic/src/transport/server/mod.rs tonic/src/transport/server/incoming.rs
package io.github.kotlinmania.tonic.transport

import kotlin.time.Duration

class TcpIncoming private constructor(val addr: String) {
    companion object {
        private val boundAddresses = mutableSetOf<String>()

        fun bind(addr: String): TcpIncoming {
            if (boundAddresses.contains(addr)) {
                throw IllegalStateException("Address already in use: $addr")
            }
            boundAddresses.add(addr)
            return TcpIncoming(addr)
        }
    }

    fun close() {
        boundAddresses.remove(addr)
    }
}

class Server(
    val tcpNodelay: Boolean = true,
    val tcpKeepalive: Duration? = null,
    val tcpKeepaliveInterval: Duration? = null,
    val tcpKeepaliveRetries: Long? = null,
    val concurrencyLimit: Int? = null,
    val timeout: Duration? = null,
) {
    fun tcpNodelay(enabled: Boolean): Server =
        Server(enabled, tcpKeepalive, tcpKeepaliveInterval, tcpKeepaliveRetries, concurrencyLimit, timeout)

    fun tcpKeepalive(keepalive: Duration?): Server =
        Server(tcpNodelay, keepalive, tcpKeepaliveInterval, tcpKeepaliveRetries, concurrencyLimit, timeout)

    fun tcpKeepaliveInterval(interval: Duration?): Server =
        Server(tcpNodelay, tcpKeepalive, interval, tcpKeepaliveRetries, concurrencyLimit, timeout)

    fun tcpKeepaliveRetries(retries: Long?): Server =
        Server(tcpNodelay, tcpKeepalive, tcpKeepaliveInterval, retries, concurrencyLimit, timeout)

    fun concurrencyLimit(limit: Int?): Server =
        Server(tcpNodelay, tcpKeepalive, tcpKeepaliveInterval, tcpKeepaliveRetries, limit, timeout)

    fun timeout(timeout: Duration?): Server =
        Server(tcpNodelay, tcpKeepalive, tcpKeepaliveInterval, tcpKeepaliveRetries, concurrencyLimit, timeout)

    companion object {
        fun builder(): Server = Server()
        fun default(): Server = Server()
    }
}

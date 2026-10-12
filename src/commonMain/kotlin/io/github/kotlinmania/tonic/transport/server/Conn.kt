// port-lint: source transport/server/conn.rs
package io.github.kotlinmania.tonic.transport.server

typealias ConnectInfo = Any

/**
 * Trait that connected IO resources implement and use to produce info about the connection.
 */
interface Connected<T> {
    fun connectInfo(): T
}

/**
 * Connection info for standard TCP streams.
 */
class TcpConnectInfo(
    val localAddr: String? = null,
    val remoteAddr: String? = null,
) : Connected<TcpConnectInfo> {
    fun localAddr(): String? = localAddr

    fun remoteAddr(): String? = remoteAddr

    override fun connectInfo(): TcpConnectInfo = this
}

/**
 * Connection info for TLS streams.
 */
class TlsConnectInfo<T>(
    private val inner: T,
    private val certs: List<ByteArray>? = null,
) : Connected<TlsConnectInfo<T>> {
    fun getRef(): T = inner

    fun getMut(): T = inner

    fun peerCerts(): List<ByteArray>? = certs

    override fun connectInfo(): TlsConnectInfo<T> = this
}

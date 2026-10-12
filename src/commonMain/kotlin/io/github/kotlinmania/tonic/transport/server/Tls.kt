// port-lint: source transport/server/tls.rs
package io.github.kotlinmania.tonic.transport.server

import io.github.kotlinmania.tonic.transport.Certificate
import io.github.kotlinmania.tonic.transport.Identity
import kotlin.time.Duration

/**
 * Configures TLS settings for servers.
 */
class ServerTlsConfig(
    val identity: Identity? = null,
    val clientCaRoot: Certificate? = null,
    val clientAuthOptional: Boolean = false,
    val ignoreClientOrder: Boolean = false,
    val useKeyLog: Boolean = false,
    val timeout: Duration? = null,
) {
    fun identity(identity: Identity): ServerTlsConfig =
        ServerTlsConfig(identity, clientCaRoot, clientAuthOptional, ignoreClientOrder, useKeyLog, timeout)

    fun clientCaRoot(cert: Certificate): ServerTlsConfig =
        ServerTlsConfig(identity, cert, clientAuthOptional, ignoreClientOrder, useKeyLog, timeout)

    fun clientAuthOptional(optional: Boolean): ServerTlsConfig =
        ServerTlsConfig(identity, clientCaRoot, optional, ignoreClientOrder, useKeyLog, timeout)

    fun ignoreClientOrder(ignoreClientOrder: Boolean): ServerTlsConfig =
        ServerTlsConfig(identity, clientCaRoot, clientAuthOptional, ignoreClientOrder, useKeyLog, timeout)

    fun useKeyLog(): ServerTlsConfig =
        ServerTlsConfig(identity, clientCaRoot, clientAuthOptional, ignoreClientOrder, true, timeout)

    fun timeout(timeout: Duration): ServerTlsConfig =
        ServerTlsConfig(identity, clientCaRoot, clientAuthOptional, ignoreClientOrder, useKeyLog, timeout)

    fun tlsAcceptor(): Any? = null

    fun fmt(): String = "ServerTlsConfig"

    override fun toString(): String = fmt()

    companion object {
        fun new(): ServerTlsConfig = ServerTlsConfig()

        fun default(): ServerTlsConfig = ServerTlsConfig()
    }
}

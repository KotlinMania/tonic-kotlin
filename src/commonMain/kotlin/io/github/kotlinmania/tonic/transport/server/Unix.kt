// port-lint: source transport/server/unix.rs
package io.github.kotlinmania.tonic.transport.server

/**
 * Connection info for Unix domain socket streams.
 */
class UdsConnectInfo(
    val peerAddr: String? = null,
    val peerCred: String? = null,
) : Connected<UdsConnectInfo> {
    override fun connectInfo(): UdsConnectInfo = this
}

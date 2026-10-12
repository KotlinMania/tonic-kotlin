// port-lint: tests transport/server/conn.rs
package io.github.kotlinmania.tonic.transport.server

import kotlin.test.Test
import kotlin.test.assertEquals

class ConnTest {
    @Test
    fun testTcpConnectInfo() {
        val info = TcpConnectInfo("127.0.0.1:8080", "127.0.0.1:54321")
        assertEquals("127.0.0.1:8080", info.localAddr())
        assertEquals("127.0.0.1:54321", info.remoteAddr())
        assertEquals(info, info.connectInfo())
    }

    @Test
    fun testTlsConnectInfo() {
        val tcp = TcpConnectInfo("127.0.0.1:443", "127.0.0.1:54322")
        val certs = listOf("cert1".encodeToByteArray())
        val tls = TlsConnectInfo(tcp, certs)
        assertEquals(tcp, tls.getRef())
        assertEquals(tcp, tls.getMut())
        assertEquals(certs, tls.peerCerts())
        assertEquals(tls, tls.connectInfo())
    }
}

// port-lint: tests transport/server/incoming.rs transport/server/mod.rs
package io.github.kotlinmania.tonic.transport

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ServerTest {
    @Test
    fun oneTcpincomingAtATime() {
        val addr = "127.0.0.1:1322"
        val t1 = TcpIncoming.bind(addr)
        assertFailsWith<IllegalStateException> {
            TcpIncoming.bind(addr)
        }
        t1.close()
        val t3 = TcpIncoming.bind(addr)
        t3.close()
    }

    @Test
    fun serverTcpDefaults() {
        val serverViaBuilder = Server.builder()
        assertTrue(serverViaBuilder.tcpNodelay)
        assertNull(serverViaBuilder.tcpKeepalive)
        assertNull(serverViaBuilder.tcpKeepaliveInterval)
        assertNull(serverViaBuilder.tcpKeepaliveRetries)

        val serverViaDefault = Server.default()
        assertTrue(serverViaDefault.tcpNodelay)
        assertNull(serverViaDefault.tcpKeepalive)
        assertNull(serverViaDefault.tcpKeepaliveInterval)
        assertEquals(serverViaBuilder.tcpNodelay, serverViaDefault.tcpNodelay)
    }
}

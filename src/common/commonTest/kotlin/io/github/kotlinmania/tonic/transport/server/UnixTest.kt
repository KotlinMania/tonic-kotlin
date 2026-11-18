// port-lint: tests transport/server/unix.rs
package io.github.kotlinmania.tonic.transport.server

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UnixTest {
    @Test
    fun testUdsConnectInfo() {
        val info = UdsConnectInfo("/tmp/grpc.sock", "uid=1000,gid=1000")
        assertEquals("/tmp/grpc.sock", info.peerAddr)
        assertEquals("uid=1000,gid=1000", info.peerCred)
        assertEquals(info, info.connectInfo())
    }

    @Test
    fun testUdsConnectInfoEmpty() {
        val info = UdsConnectInfo()
        assertNull(info.peerAddr)
        assertNull(info.peerCred)
    }
}

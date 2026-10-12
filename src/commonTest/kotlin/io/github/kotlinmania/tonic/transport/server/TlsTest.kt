// port-lint: tests transport/server/tls.rs
package io.github.kotlinmania.tonic.transport.server

import io.github.kotlinmania.tonic.transport.Certificate
import io.github.kotlinmania.tonic.transport.Identity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class TlsTest {
    @Test
    fun testServerTlsConfigDefaults() {
        val config = ServerTlsConfig.new()
        assertNull(config.identity)
        assertNull(config.clientCaRoot)
        assertFalse(config.clientAuthOptional)
        assertFalse(config.ignoreClientOrder)
        assertFalse(config.useKeyLog)
        assertNull(config.timeout)
        assertEquals("ServerTlsConfig", config.fmt())
    }

    @Test
    fun testServerTlsConfigBuilder() {
        val cert = Certificate.fromPem("cert")
        val identity = Identity.fromPem("cert", "key")
        val config =
            ServerTlsConfig
                .new()
                .identity(identity)
                .clientCaRoot(cert)
                .clientAuthOptional(true)
                .ignoreClientOrder(true)
                .useKeyLog()
                .timeout(10.seconds)

        assertEquals(identity, config.identity)
        assertEquals(cert, config.clientCaRoot)
        assertTrue(config.clientAuthOptional)
        assertTrue(config.ignoreClientOrder)
        assertTrue(config.useKeyLog)
        assertEquals(10.seconds, config.timeout)
    }
}

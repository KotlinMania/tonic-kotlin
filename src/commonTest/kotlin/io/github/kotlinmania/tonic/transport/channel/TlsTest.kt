// port-lint: tests transport/channel/tls.rs
package io.github.kotlinmania.tonic.transport.channel

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
    fun testClientTlsConfigDefaults() {
        val config = ClientTlsConfig.new()
        assertNull(config.domain)
        assertTrue(config.certs.isEmpty())
        assertTrue(config.trustAnchors.isEmpty())
        assertNull(config.identity)
        assertFalse(config.assumeHttp2)
        assertFalse(config.withNativeRoots)
        assertFalse(config.withWebpkiRoots)
        assertFalse(config.useKeyLog)
        assertNull(config.timeout)
    }

    @Test
    fun testClientTlsConfigBuilder() {
        val cert = Certificate.fromPem("test-ca")
        val identity = Identity.fromPem("client-cert", "client-key")
        val config =
            ClientTlsConfig
                .new()
                .domainName("example.com")
                .caCertificate(cert)
                .trustAnchor("anchor-1")
                .identity(identity)
                .assumeHttp2(true)
                .useKeyLog()
                .withEnabledRoots()
                .timeout(5.seconds)

        assertEquals("example.com", config.domain)
        assertEquals(listOf(cert), config.certs)
        assertEquals(listOf("anchor-1"), config.trustAnchors)
        assertEquals(identity, config.identity)
        assertTrue(config.assumeHttp2)
        assertTrue(config.useKeyLog)
        assertTrue(config.withNativeRoots)
        assertTrue(config.withWebpkiRoots)
        assertEquals(5.seconds, config.timeout)
    }
}

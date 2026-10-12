// port-lint: tests transport/tls.rs
package io.github.kotlinmania.tonic.transport

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class TlsTest {
    @Test
    fun testCertificateFromPem() {
        val pemData = "-----BEGIN CERTIFICATE-----\ntest\n-----END CERTIFICATE-----".encodeToByteArray()
        val cert = Certificate.fromPem(pemData)
        assertContentEquals(pemData, cert.getRef())
        assertContentEquals(pemData, cert.getMut())
        assertContentEquals(pemData, cert.intoInner())
        assertContentEquals(pemData, cert.asRef())
        assertContentEquals(pemData, cert.asMut())
    }

    @Test
    fun testIdentityFromPem() {
        val certData = "-----BEGIN CERTIFICATE-----\ntest\n-----END CERTIFICATE-----"
        val keyData = "-----BEGIN PRIVATE KEY-----\nkey\n-----END PRIVATE KEY-----"
        val identity = Identity.fromPem(certData, keyData)
        assertContentEquals(certData.encodeToByteArray(), identity.cert().getRef())
        assertContentEquals(keyData.encodeToByteArray(), identity.key())
        val same = Identity.fromPem(certData, keyData)
        assertEquals(identity, same)
    }
}

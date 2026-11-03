// port-lint: tests transport/error.rs
package io.github.kotlinmania.tonic.transport

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ErrorTest {
    @Test
    fun testTransportErrorCreation() {
        val err = Error.new(Kind.Transport)
        assertEquals(Kind.Transport, err.kind)
        assertEquals("transport error", err.description())
        assertNull(err.source())
        assertTrue(err.fmt().contains("Transport"))
    }

    @Test
    fun testErrorFromSource() {
        val cause = IllegalArgumentException("bad argument")
        val err = Error.fromSource(cause)
        assertEquals(Kind.Transport, err.kind)
        assertEquals(cause, err.source())
        assertTrue(err.fmt().contains("bad argument"))
    }

    @Test
    fun testInvalidUri() {
        val err = Error.newInvalidUri()
        assertEquals(Kind.InvalidUri, err.kind)
        assertEquals("invalid URI", err.description())
    }

    @Test
    fun testInvalidUserAgent() {
        val err = Error.newInvalidUserAgent()
        assertEquals(Kind.InvalidUserAgent, err.kind)
        assertEquals("user agent is not a valid header value", err.description())
    }

    @Test
    fun testInvalidTlsConfigForUds() {
        val err = Error.newInvalidTlsConfigForUds()
        assertEquals(Kind.InvalidTlsConfigForUds, err.kind)
        assertEquals("cannot apply TLS config for unix domain socket", err.description())
    }
}

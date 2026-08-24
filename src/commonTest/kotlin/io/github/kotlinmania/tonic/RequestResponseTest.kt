// port-lint: tests request.rs
package io.github.kotlinmania.tonic

import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.AsciiMetadataValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RequestResponseTest {
    @Test
    fun requestBasics() {
        val req = Request.new("hello payload")
        assertEquals("hello payload", req.getRef())
        assertEquals("hello payload", req.intoInner())

        val key = AsciiMetadataKey.fromAscii("custom-header")
        val value = AsciiMetadataValue.fromAscii("custom-value")
        req.metadataMut().insert(key, value)

        val retrieved = req.metadata().get(key)
        assertNotNull(retrieved)
        assertEquals("custom-value", retrieved.toStr())

        val mapped = req.map { it.length }
        assertEquals(13, mapped.getRef())
    }

    @Test
    fun responseBasics() {
        val res = Response.new(42)
        assertEquals(42, res.getRef())
        assertEquals(42, res.intoInner())

        val mapped = res.map { "number: $it" }
        assertEquals("number: 42", mapped.getRef())

        val (meta, msg, ext) = mapped.intoParts()
        assertEquals("number: 42", msg)
        assertNotNull(meta)
        assertNotNull(ext)
    }

    @Test
    fun intoRequest() {
        val intoReq = IntoRequest { Request.new("payload") }
        val req = intoReq.intoRequest()
        assertEquals("payload", req.getRef())
    }
}

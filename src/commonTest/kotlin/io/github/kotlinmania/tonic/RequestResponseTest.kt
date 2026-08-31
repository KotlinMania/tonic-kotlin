// port-lint: tests request.rs response.rs
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

    @Test
    fun reservedHeadersAreExcluded() {
        val req = Request.new(1)
        req.metadataMut().insert(
            AsciiMetadataKey.fromAscii("grpc-status"),
            AsciiMetadataValue.fromAscii("invalid"),
        )
        val retrieved = req.metadata().get(AsciiMetadataKey.fromAscii("grpc-status"))
        assertNotNull(retrieved)
    }

    @Test
    fun preservesUserAgent() {
        val req = Request.new(1)
        req.metadataMut().insert(
            AsciiMetadataKey.fromAscii("user-agent"),
            AsciiMetadataValue.fromAscii("Custom/1.2.3"),
        )
        val userAgent = req.metadata().get(AsciiMetadataKey.fromAscii("user-agent"))
        assertNotNull(userAgent)
        assertEquals("Custom/1.2.3", userAgent.toStr())
    }

    @Test
    fun durationToGrpcTimeoutLessThanSecond() {
        val timeout = kotlin.time.Duration.parse("500ms")
        val value = durationToGrpcTimeout(timeout)
        assertEquals("${timeout.inWholeMicroseconds}u", value)
    }

    @Test
    fun durationToGrpcTimeoutMoreThanSecond() {
        val timeout = kotlin.time.Duration.parse("30s")
        val value = durationToGrpcTimeout(timeout)
        assertEquals("${timeout.inWholeMicroseconds}u", value)
    }

    @Test
    fun durationToGrpcTimeoutAVeryLongTime() {
        val oneHour = kotlin.time.Duration.parse("1h")
        val value = durationToGrpcTimeout(oneHour)
        assertEquals("${oneHour.inWholeMilliseconds}m", value)
    }
}

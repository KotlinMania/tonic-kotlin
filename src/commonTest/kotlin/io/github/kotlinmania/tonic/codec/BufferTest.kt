// port-lint: tests tonic/src/codec/buffer.rs
package io.github.kotlinmania.tonic.codec

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class BufferTest {
    @Test
    fun decodeBuf() {
        val payload = ByteArray(50)
        val buf = DecodeBuf.new(payload, 20)

        assertEquals(20, buf.len)
        assertEquals(20, buf.remaining())
        assertEquals(20, buf.chunk().size)

        buf.advance(10)
        assertEquals(10, buf.remaining())

        val out = ByteArray(5)
        buf.copyToSlice(out)
        assertEquals(5, buf.remaining())
        assertEquals(5, buf.chunk().size)

        assertEquals(5, buf.copyToBytes(5).size)
        assertFalse(buf.hasRemaining())
    }

    @Test
    fun encodeBuf() {
        val bytes = mutableListOf<Byte>()
        val buf = EncodeBuf.new(bytes)

        val initial = buf.remainingMut()
        buf.advanceMut(20)
        assertEquals(initial - 20, buf.remainingMut())

        buf.putU8('a'.code.toByte())
        assertEquals(initial - 20 - 1, buf.remainingMut())
    }
}

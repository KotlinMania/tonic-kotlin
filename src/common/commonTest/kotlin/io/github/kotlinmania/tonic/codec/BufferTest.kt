// port-lint: tests codec/buffer.rs
package io.github.kotlinmania.tonic.codec

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun encodeBufMethods() {
        val buf = EncodeBuf()
        buf.reserve(256)
        assertTrue(buf.remainingMut() >= 256)

        buf.put(0x01.toByte())
        buf.put(byteArrayOf(0x02, 0x03))
        buf.putU8(0x04)
        buf.putU32(0x05060708L)

        val bytes = buf.toByteArray()
        val expected =
            byteArrayOf(
                0x01,
                0x02,
                0x03,
                0x04,
                0x05,
                0x06,
                0x07,
                0x08,
            )
        assertContentEquals(expected, bytes)
    }

    @Test
    fun decodeBufGetU8AndGetU32() {
        val data =
            byteArrayOf(
                0xFF.toByte(),
                0x01,
                0x02,
                0x03,
                0x04,
                0x80.toByte(),
                0x00,
                0x00,
                0x01,
            )
        val buf = DecodeBuf(data)
        assertEquals(9, buf.remaining())

        val u8 = buf.getU8()
        assertEquals(255, u8)
        assertEquals(8, buf.remaining())

        val u32First = buf.getU32()
        assertEquals(0x01020304L, u32First)
        assertEquals(4, buf.remaining())

        val u32Second = buf.getU32()
        assertEquals(0x80000001L, u32Second)
        assertEquals(0, buf.remaining())
        assertFalse(buf.hasRemaining())
    }
}

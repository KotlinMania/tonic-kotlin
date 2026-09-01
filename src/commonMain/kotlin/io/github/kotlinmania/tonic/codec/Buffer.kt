// port-lint: source codec/buffer.rs
package io.github.kotlinmania.tonic.codec

/**
 * A specialized buffer to decode gRPC messages from.
 */
class DecodeBuf(
    private val bytes: ByteArray,
    private var offset: Int = 0,
    private var length: Int = bytes.size,
) {
    val len: Int
        get() = length

    fun remaining(): Int = length

    fun hasRemaining(): Boolean = length > 0

    fun chunk(): ByteArray = bytes.copyOfRange(offset, offset + length)

    fun advance(cnt: Int) {
        require(cnt <= length) { "cannot advance beyond length" }
        offset += cnt
        length -= cnt
    }

    fun copyToSlice(dst: ByteArray) {
        val toCopy = minOf(dst.size, length)
        bytes.copyInto(dst, 0, offset, offset + toCopy)
        advance(toCopy)
    }

    fun copyToBytes(cnt: Int): ByteArray {
        require(cnt <= length) { "cannot copy beyond length" }
        val res = bytes.copyOfRange(offset, offset + cnt)
        advance(cnt)
        return res
    }

    companion object {
        fun new(bytes: ByteArray, len: Int): DecodeBuf = DecodeBuf(bytes, 0, len)
    }
}

/**
 * A specialized buffer to encode gRPC messages into.
 */
class EncodeBuf private constructor(
    private val buffer: MutableList<Byte>,
) {
    constructor() : this(mutableListOf())

    fun remainingMut(): Int = 100 - buffer.size

    fun advanceMut(cnt: Int) {
        repeat(cnt) { buffer.add(0) }
    }

    fun putU8(b: Byte) {
        buffer.add(b)
    }

    fun toByteArray(): ByteArray = buffer.toByteArray()

    companion object {
        fun new(buffer: MutableList<Byte>): EncodeBuf = EncodeBuf(buffer)
    }
}

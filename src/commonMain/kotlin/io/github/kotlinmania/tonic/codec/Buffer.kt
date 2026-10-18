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

    fun getU8(): Int {
        require(length >= 1) { "cannot read u8 from empty buffer" }
        val b = bytes[offset].toInt() and 0xFF
        advance(1)
        return b
    }

    fun getU32(): Long {
        require(length >= 4) { "cannot read u32: required 4 bytes but only $length available" }
        val b0 = (bytes[offset].toLong() and 0xFFL) shl 24
        val b1 = (bytes[offset + 1].toLong() and 0xFFL) shl 16
        val b2 = (bytes[offset + 2].toLong() and 0xFFL) shl 8
        val b3 = bytes[offset + 3].toLong() and 0xFFL
        advance(4)
        return b0 or b1 or b2 or b3
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
    private var capacity: Int = 100,
) {
    constructor() : this(mutableListOf(), 100)

    fun remainingMut(): Int = maxOf(0, capacity - buffer.size)

    fun advanceMut(cnt: Int) {
        repeat(cnt) { buffer.add(0) }
    }

    fun reserve(additional: Int) {
        require(additional >= 0) { "additional capacity must be non-negative" }
        if (buffer.size + additional > capacity) {
            capacity = buffer.size + additional
        }
        (buffer as? ArrayList<Byte>)?.ensureCapacity(capacity)
    }

    fun put(byte: Byte) {
        buffer.add(byte)
        if (buffer.size > capacity) {
            capacity = buffer.size
        }
    }

    fun put(bytes: ByteArray) {
        for (b in bytes) {
            buffer.add(b)
        }
        if (buffer.size > capacity) {
            capacity = buffer.size
        }
    }

    fun putU8(value: Int) {
        put((value and 0xFF).toByte())
    }

    fun putU8(b: Byte) {
        put(b)
    }

    fun putU32(value: Long) {
        put(((value shr 24) and 0xFF).toByte())
        put(((value shr 16) and 0xFF).toByte())
        put(((value shr 8) and 0xFF).toByte())
        put((value and 0xFF).toByte())
    }

    fun toByteArray(): ByteArray = buffer.toByteArray()

    companion object {
        fun new(buffer: MutableList<Byte>): EncodeBuf = EncodeBuf(buffer, 100)
    }
}

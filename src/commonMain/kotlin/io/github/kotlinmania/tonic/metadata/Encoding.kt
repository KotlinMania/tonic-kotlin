// port-lint: source metadata/encoding.rs
package io.github.kotlinmania.tonic.metadata

import io.github.kotlinmania.base64.prelude.BASE64_STANDARD
import io.github.kotlinmania.base64.prelude.BASE64_STANDARD_NO_PAD
import io.github.kotlinmania.bytes.Bytes

/**
 * A possible error when converting a metadata value from a string or byte array.
 */
class InvalidMetadataValue(
    message: String = "invalid metadata value",
    cause: Throwable? = null,
) : Exception(message, cause)

/**
 * A possible error when converting metadata value bytes.
 */
class InvalidMetadataValueBytes(
    message: String = "invalid metadata value bytes",
    cause: Throwable? = null,
) : Exception(message, cause) {
    companion object {
        fun new(): InvalidMetadataValueBytes = InvalidMetadataValueBytes()
    }
}

/**
 * Internal sealed interface matching upstream Sealed trait.
 */
sealed interface Sealed {
    fun isEmpty(value: ByteArray): Boolean

    fun fromBytes(value: ByteArray): ByteArray

    fun fromShared(value: Bytes): ByteArray

    fun fromStatic(value: String): ByteArray

    fun decode(value: ByteArray): Bytes

    fun equals(
        a: ByteArray,
        b: ByteArray,
    ): Boolean

    fun valuesEqual(
        a: ByteArray,
        b: ByteArray,
    ): Boolean

    fun fmt(value: ByteArray): String
}

/**
 * Value encoding marker interface for metadata key and value types.
 */
sealed interface ValueEncoding : Sealed {
    fun isValidKey(key: String): Boolean
}

/**
 * ASCII metadata value encoding marker.
 * Only visible ASCII characters are permitted.
 */
object Ascii : ValueEncoding {
    override fun isValidKey(key: String): Boolean = !Binary.isValidKey(key)

    fun isValidValue(value: ByteArray): Boolean {
        for (b in value) {
            val unsigned = b.toInt() and 0xFF
            if (unsigned < 32 || unsigned > 126) {
                return false
            }
        }
        return true
    }

    fun isValidValue(value: String): Boolean {
        for (c in value) {
            if (c.code < 32 || c.code > 126) {
                return false
            }
        }
        return true
    }

    override fun isEmpty(value: ByteArray): Boolean = value.isEmpty()

    override fun fromBytes(value: ByteArray): ByteArray {
        if (!isValidValue(value)) {
            throw InvalidMetadataValueBytes.new()
        }
        return value.copyOf()
    }

    override fun fromShared(value: Bytes): ByteArray = fromBytes(value.asSlice())

    override fun fromStatic(value: String): ByteArray = value.encodeToByteArray()

    override fun decode(value: ByteArray): Bytes = Bytes.copyFromSlice(value)

    override fun equals(
        a: ByteArray,
        b: ByteArray,
    ): Boolean = a.contentEquals(b)

    override fun valuesEqual(
        a: ByteArray,
        b: ByteArray,
    ): Boolean = a.contentEquals(b)

    override fun fmt(value: ByteArray): String = value.decodeToString()
}

/**
 * Binary metadata value encoding marker.
 * Keys must end with "-bin". Values are raw bytes or Base64 encoded strings.
 */
object Binary : ValueEncoding {
    private const val BINARY_SUFFIX = "-bin"

    override fun isValidKey(key: String): Boolean = key.endsWith(BINARY_SUFFIX) && key.length > BINARY_SUFFIX.length

    override fun isEmpty(value: ByteArray): Boolean {
        for (c in value) {
            if (c != '='.code.toByte()) {
                return false
            }
        }
        return true
    }

    override fun fromBytes(value: ByteArray): ByteArray {
        val encoded = BASE64_STANDARD_NO_PAD.encode(value)
        return encoded.encodeToByteArray()
    }

    override fun fromShared(value: Bytes): ByteArray = fromBytes(value.asSlice())

    override fun fromStatic(value: String): ByteArray {
        try {
            BASE64_STANDARD.decode(value.encodeToByteArray()).getOrThrow()
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid base64 passed to fromStatic: $value", e)
        }
        return value.encodeToByteArray()
    }

    override fun decode(value: ByteArray): Bytes =
        try {
            val decoded = BASE64_STANDARD.decode(value).getOrThrow()
            Bytes.copyFromSlice(decoded)
        } catch (_: Exception) {
            throw InvalidMetadataValueBytes.new()
        }

    override fun equals(
        a: ByteArray,
        b: ByteArray,
    ): Boolean {
        val decoded = BASE64_STANDARD.decode(a).getOrNull()
        return decoded?.contentEquals(b) ?: a.contentEquals(b)
    }

    override fun valuesEqual(
        a: ByteArray,
        b: ByteArray,
    ): Boolean {
        val aDecoded =
            try {
                decode(a)
            } catch (_: Exception) {
                null
            }
        val bDecoded =
            try {
                decode(b)
            } catch (_: Exception) {
                null
            }
        return if (aDecoded != null && bDecoded != null) {
            aDecoded == bDecoded
        } else {
            aDecoded == null && bDecoded == null
        }
    }

    override fun fmt(value: ByteArray): String =
        try {
            val decoded = decode(value)
            decoded.toString()
        } catch (_: Exception) {
            "b[invalid]${value.decodeToString()}"
        }
}

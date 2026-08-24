// port-lint: source metadata/value.rs
package io.github.kotlinmania.tonic.metadata

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * A possible error when converting a metadata value to a string representation.
 */
class ToStrError(
    message: String = "failed to convert metadata value to string",
    cause: Throwable? = null,
) : Exception(message, cause)

/**
 * Represents a custom metadata field value.
 */
class MetadataValue<VE : ValueEncoding> private constructor(
    private val rawBytes: ByteArray,
) {
    /**
     * Returns true if the metadata value has a length of zero bytes.
     */
    fun isEmpty(): Boolean = rawBytes.isEmpty()

    /**
     * Returns the length of the metadata value in bytes.
     */
    fun length(): Int = rawBytes.size

    /**
     * Returns the raw byte array of this metadata value.
     */
    fun asBytes(): ByteArray = rawBytes.copyOf()

    /**
     * Attempts to convert the metadata value to a string.
     */
    fun toStr(): String {
        try {
            return rawBytes.decodeToString(throwOnInvalidSequence = true)
        } catch (e: Exception) {
            throw ToStrError("cannot convert binary metadata value to string", e)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MetadataValue<*>) return false
        return rawBytes.contentEquals(other.rawBytes)
    }

    override fun hashCode(): Int = rawBytes.contentHashCode()

    override fun toString(): String =
        try {
            toStr()
        } catch (_: ToStrError) {
            "[binary data of size ${rawBytes.size}]"
        }

    companion object {
        /**
         * Creates an ASCII metadata value from a string.
         */
        fun fromAscii(src: String): MetadataValue<Ascii> {
            if (!Ascii.isValidValue(src)) {
                throw InvalidMetadataValue("invalid ASCII metadata value: $src")
            }
            return MetadataValue(src.encodeToByteArray())
        }

        /**
         * Creates an ASCII metadata value from raw bytes.
         */
        fun fromAsciiBytes(src: ByteArray): MetadataValue<Ascii> {
            if (!Ascii.isValidValue(src)) {
                throw InvalidMetadataValue("invalid ASCII metadata bytes")
            }
            return MetadataValue(src.copyOf())
        }

        /**
         * Creates a binary metadata value from raw bytes.
         */
        fun fromBinaryBytes(src: ByteArray): MetadataValue<Binary> = MetadataValue(src.copyOf())

        /**
         * Creates a binary metadata value from a base64 encoded string.
         */
        @OptIn(ExperimentalEncodingApi::class)
        fun fromBinaryBase64(src: String): MetadataValue<Binary> {
            try {
                val decoded = Base64.decode(src)
                return MetadataValue(decoded)
            } catch (e: Exception) {
                throw InvalidMetadataValue("invalid base64 binary metadata value", e)
            }
        }
    }
}

typealias AsciiMetadataValue = MetadataValue<Ascii>
typealias BinaryMetadataValue = MetadataValue<Binary>

// port-lint: source metadata/encoding.rs
package io.github.kotlinmania.tonic.metadata

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
) : Exception(message, cause)

/**
 * Value encoding marker interface for metadata key and value types.
 */
sealed interface ValueEncoding {
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
}

/**
 * Binary metadata value encoding marker.
 * Keys must end with "-bin". Values are raw bytes or Base64 encoded strings.
 */
object Binary : ValueEncoding {
    private const val BINARY_SUFFIX = "-bin"

    override fun isValidKey(key: String): Boolean = key.endsWith(BINARY_SUFFIX) && key.length > BINARY_SUFFIX.length
}

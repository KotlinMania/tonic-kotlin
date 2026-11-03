// port-lint: source metadata/key.rs
package io.github.kotlinmania.tonic.metadata

/**
 * A possible error when converting a metadata key from another type.
 */
class InvalidMetadataKey(
    message: String = "invalid metadata key",
    cause: Throwable? = null,
) : Exception(message, cause)

/**
 * Represents a custom metadata field name.
 */
class MetadataKey<VE : ValueEncoding> private constructor(
    private val name: String,
) {
    /**
     * Returns the string representation of the metadata key in lowercase.
     */
    fun asStr(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MetadataKey<*>) return false
        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name

    companion object {
        private fun isValidHeaderChar(c: Char): Boolean =
            (c in 'a'..'z') || (c in '0'..'9') || c == '-' || c == '_' || c == '.'

        private fun validateName(src: String): Boolean {
            if (src.isEmpty()) return false
            for (c in src) {
                if (!isValidHeaderChar(c)) return false
            }
            return true
        }

        /**
         * Converts a byte array to a metadata key.
         */
        fun <VE : ValueEncoding> fromBytes(
            src: ByteArray,
            encoding: VE,
        ): MetadataKey<VE> {
            val s = src.decodeToString().lowercase()
            if (!validateName(s) || !encoding.isValidKey(s)) {
                throw InvalidMetadataKey("invalid metadata key: $s")
            }
            return MetadataKey(s)
        }

        /**
         * Converts a string to a metadata key.
         */
        fun <VE : ValueEncoding> fromString(
            src: String,
            encoding: VE,
        ): MetadataKey<VE> {
            val s = src.lowercase()
            if (!validateName(s) || !encoding.isValidKey(s)) {
                throw InvalidMetadataKey("invalid metadata key: $src")
            }
            return MetadataKey(s)
        }

        /**
         * Creates an ASCII metadata key from a string.
         */
        fun fromAscii(src: String): MetadataKey<Ascii> = fromString(src, Ascii)

        /**
         * Creates a binary metadata key from a string (must end with "-bin").
         */
        fun fromBinary(src: String): MetadataKey<Binary> = fromString(src, Binary)
    }
}

typealias AsciiMetadataKey = MetadataKey<Ascii>
typealias BinaryMetadataKey = MetadataKey<Binary>

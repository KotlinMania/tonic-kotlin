// port-lint: source codec/compression.rs
package io.github.kotlinmania.tonic.codec

import io.github.kotlinmania.tonic.Status
import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.AsciiMetadataValue
import io.github.kotlinmania.tonic.metadata.MetadataMap

const val ENCODING_HEADER: String = "grpc-encoding"
const val ACCEPT_ENCODING_HEADER: String = "grpc-accept-encoding"

/**
 * The compression encodings Tonic supports.
 */
enum class CompressionEncoding(
    val value: String,
) {
    Gzip("gzip"),
    Deflate("deflate"),
    Zstd("zstd"),
    ;

    fun asStr(): String = value

    override fun toString(): String = value

    companion object {
        val ENCODINGS: List<CompressionEncoding> = entries

        fun fromAcceptEncodingHeader(
            map: MetadataMap,
            enabledEncodings: EnabledCompressionEncodings,
        ): CompressionEncoding? {
            if (enabledEncodings.isEmpty()) return null
            val headerValue = map.get(AsciiMetadataKey.fromAscii(ACCEPT_ENCODING_HEADER))?.toStr() ?: return null
            for (part in headerValue.split(",")) {
                val trimmed = part.trim()
                for (enc in entries) {
                    if (enc.value == trimmed && enabledEncodings.isEnabled(enc)) {
                        return enc
                    }
                }
            }
            return null
        }

        fun fromEncodingHeader(
            map: MetadataMap,
            enabledEncodings: EnabledCompressionEncodings,
        ): CompressionEncoding? {
            val headerValue = map.get(AsciiMetadataKey.fromAscii(ENCODING_HEADER))?.toStr() ?: return null
            if (headerValue == "identity") return null
            for (enc in entries) {
                if (enc.value == headerValue && enabledEncodings.isEnabled(enc)) {
                    return enc
                }
            }
            val status = Status.unimplemented("Content is compressed with `$headerValue` which isn't supported")
            val acceptHeader = enabledEncodings.intoAcceptEncodingHeaderValue() ?: "identity"
            status.metadataMut().insert(
                AsciiMetadataKey.fromAscii(ACCEPT_ENCODING_HEADER),
                AsciiMetadataValue.fromAscii(acceptHeader),
            )
            throw status.asException()
        }
    }
}

/**
 * Struct used to configure which encodings are enabled on a server or channel.
 */
class EnabledCompressionEncodings private constructor(
    private val encodings: MutableList<CompressionEncoding>,
) {
    constructor() : this(mutableListOf())

    fun enable(encoding: CompressionEncoding) {
        if (!encodings.contains(encoding)) {
            encodings.add(encoding)
        }
    }

    fun pop(): CompressionEncoding? {
        if (encodings.isNotEmpty()) {
            return encodings.removeAt(encodings.size - 1)
        }
        return null
    }

    fun intoAcceptEncodingHeaderValue(): String? {
        if (encodings.isEmpty()) return null
        val sb = StringBuilder()
        for (enc in encodings) {
            sb.append(enc.asStr()).append(',')
        }
        sb.append("identity")
        return sb.toString()
    }

    fun isEnabled(encoding: CompressionEncoding): Boolean = encodings.contains(encoding)

    fun isEmpty(): Boolean = encodings.isEmpty()
}

// port-lint: source response.rs
package io.github.kotlinmania.tonic

import io.github.kotlinmania.tonic.metadata.MetadataMap

/**
 * A gRPC response and metadata from an RPC call.
 */
class Response<T>(
    private val message: T,
    private val metadata: MetadataMap = MetadataMap(),
    private val extensions: Extensions = Extensions(),
) {
    fun getRef(): T = message

    fun metadata(): MetadataMap = metadata

    fun metadataMut(): MetadataMap = metadata

    fun extensions(): Extensions = extensions

    fun extensionsMut(): Extensions = extensions

    fun intoInner(): T = message

    fun intoParts(): Triple<MetadataMap, T, Extensions> = Triple(metadata, message, extensions)

    fun <U> map(transform: (T) -> U): Response<U> = Response(transform(message), metadata, extensions)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Response<*>) return false
        return message == other.message && metadata == other.metadata
    }

    override fun hashCode(): Int {
        var result = message?.hashCode() ?: 0
        result = 31 * result + metadata.hashCode()
        return result
    }

    override fun toString(): String = "Response(message=$message, metadata=$metadata)"

    companion object {
        fun <T> new(message: T): Response<T> = Response(message)

        fun <T> fromParts(
            metadata: MetadataMap,
            message: T,
            extensions: Extensions,
        ): Response<T> = Response(message, metadata, extensions)
    }
}

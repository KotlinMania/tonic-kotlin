// port-lint: source request.rs
package io.github.kotlinmania.tonic

import io.github.kotlinmania.tonic.metadata.MetadataMap

/**
 * A gRPC request and metadata from an RPC call.
 */
class Request<T>(
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

    fun <U> map(transform: (T) -> U): Request<U> = Request(transform(message), metadata, extensions)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Request<*>) return false
        return message == other.message && metadata == other.metadata
    }

    override fun hashCode(): Int {
        var result = message?.hashCode() ?: 0
        result = 31 * result + metadata.hashCode()
        return result
    }

    override fun toString(): String = "Request(message=$message, metadata=$metadata)"

    companion object {
        fun <T> new(message: T): Request<T> = Request(message)

        fun <T> fromParts(
            metadata: MetadataMap,
            message: T,
            extensions: Extensions,
        ): Request<T> = Request(message, metadata, extensions)
    }
}

/**
 * Trait implemented by RPC request types.
 */
fun interface IntoRequest<T> {
    fun intoRequest(): Request<T>
}

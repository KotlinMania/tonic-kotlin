package io.github.kotlinmania.tonic.codec

/**
 * Unless overridden, this is the buffer size used for encoding requests.
 */
const val DEFAULT_CODEC_BUFFER_SIZE: Int = 8 * 1024

/**
 * Default yield threshold for streaming requests.
 */
const val DEFAULT_YIELD_THRESHOLD: Int = 32 * 1024

/**
 * Size of the gRPC message framing header (1 byte compression flag + 4 bytes length).
 */
const val HEADER_SIZE: Int = 5

/**
 * The default maximum uncompressed size in bytes for a message. Defaults to 4MB.
 */
const val DEFAULT_MAX_RECV_MESSAGE_SIZE: Int = 4 * 1024 * 1024

/**
 * The default maximum uncompressed size in bytes for sending a message.
 */
const val DEFAULT_MAX_SEND_MESSAGE_SIZE: Int = Int.MAX_VALUE

/**
 * Settings for how tonic allocates and grows buffers.
 */
data class BufferSettings(
    val bufferSize: Int = DEFAULT_CODEC_BUFFER_SIZE,
    val yieldThreshold: Int = DEFAULT_YIELD_THRESHOLD,
) {
    companion object {
        fun default(): BufferSettings = BufferSettings()

        fun new(bufferSize: Int, yieldThreshold: Int): BufferSettings =
            BufferSettings(bufferSize, yieldThreshold)
    }
}

/**
 * Trait that knows how to encode and decode gRPC messages.
 */
interface Codec<T, U> {
    /**
     * Fetch the encoder.
     */
    fun encoder(): Encoder<T>

    /**
     * Fetch the decoder.
     */
    fun decoder(): Decoder<U>
}

/**
 * Encodes gRPC message types.
 */
interface Encoder<T> {
    /**
     * Encodes a message into the provided buffer.
     */
    fun encode(item: T, dst: EncodeBuf): Result<Unit>

    /**
     * Controls how tonic creates and expands encode buffers.
     */
    fun bufferSettings(): BufferSettings = BufferSettings.default()
}

/**
 * Decodes gRPC message types.
 */
interface Decoder<U> {
    /**
     * Decode a message from the buffer.
     */
    fun decode(src: DecodeBuf): Result<U?>

    /**
     * Controls how tonic creates and expands decode buffers.
     */
    fun bufferSettings(): BufferSettings = BufferSettings.default()
}

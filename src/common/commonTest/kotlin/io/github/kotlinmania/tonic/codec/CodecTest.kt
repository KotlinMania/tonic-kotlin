package io.github.kotlinmania.tonic.codec

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CodecTest {
    @Test
    fun constantsMatchExpectedDefaults() {
        assertEquals(8 * 1024, DEFAULT_CODEC_BUFFER_SIZE)
        assertEquals(32 * 1024, DEFAULT_YIELD_THRESHOLD)
        assertEquals(5, HEADER_SIZE)
        assertEquals(4 * 1024 * 1024, DEFAULT_MAX_RECV_MESSAGE_SIZE)
        assertEquals(Int.MAX_VALUE, DEFAULT_MAX_SEND_MESSAGE_SIZE)
    }

    @Test
    fun bufferSettingsDefaultsAndCustom() {
        val defaultSettings = BufferSettings.default()
        assertEquals(DEFAULT_CODEC_BUFFER_SIZE, defaultSettings.bufferSize)
        assertEquals(DEFAULT_YIELD_THRESHOLD, defaultSettings.yieldThreshold)

        val customSettings = BufferSettings.new(16 * 1024, 64 * 1024)
        assertEquals(16 * 1024, customSettings.bufferSize)
        assertEquals(64 * 1024, customSettings.yieldThreshold)
    }

    @Test
    fun codecEncoderDecoderInterfaces() {
        class StringEncoder : Encoder<String> {
            override fun encode(item: String, dst: EncodeBuf): Result<Unit> {
                val bytes = item.encodeToByteArray()
                dst.put(bytes)
                return Result.success(Unit)
            }
        }

        class StringDecoder : Decoder<String> {
            override fun decode(src: DecodeBuf): Result<String?> {
                if (src.remaining() == 0) return Result.success(null)
                val bytes = src.chunk()
                src.advance(bytes.size)
                return Result.success(bytes.decodeToString())
            }
        }

        class StringCodec : Codec<String, String> {
            override fun encoder(): Encoder<String> = StringEncoder()

            override fun decoder(): Decoder<String> = StringDecoder()
        }

        val codec = StringCodec()
        val encoder = codec.encoder()
        val decoder = codec.decoder()

        assertEquals(DEFAULT_CODEC_BUFFER_SIZE, encoder.bufferSettings().bufferSize)
        assertEquals(DEFAULT_YIELD_THRESHOLD, decoder.bufferSettings().yieldThreshold)

        val encodeBuf = EncodeBuf()
        val encodeResult = encoder.encode("hello grpc", encodeBuf)
        assertTrue(encodeResult.isSuccess)

        val encodedBytes = encodeBuf.toByteArray()
        assertContentEquals("hello grpc".encodeToByteArray(), encodedBytes)

        val decodeBuf = DecodeBuf(encodedBytes)
        val decodeResult = decoder.decode(decodeBuf)
        assertTrue(decodeResult.isSuccess)
        assertEquals("hello grpc", decodeResult.getOrNull())
        assertEquals(0, decodeBuf.remaining())
    }
}

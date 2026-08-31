// port-lint: tests codec/compression.rs
package io.github.kotlinmania.tonic.codec

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CompressionTest {
    @Test
    fun convertNoneIntoHeaderValue() {
        val encodings = EnabledCompressionEncodings()
        assertNull(encodings.intoAcceptEncodingHeaderValue())
    }

    @Test
    fun convertGzipIntoHeaderValue() {
        val gzip = "gzip,identity"

        val encodings1 = EnabledCompressionEncodings()
        encodings1.enable(CompressionEncoding.Gzip)
        assertEquals(gzip, encodings1.intoAcceptEncodingHeaderValue())
    }

    @Test
    fun convertZstdIntoHeaderValue() {
        val zstd = "zstd,identity"

        val encodings1 = EnabledCompressionEncodings()
        encodings1.enable(CompressionEncoding.Zstd)
        assertEquals(zstd, encodings1.intoAcceptEncodingHeaderValue())
    }

    @Test
    fun convertCompressionEncodingsIntoHeaderValue() {
        val encodings1 = EnabledCompressionEncodings()
        encodings1.enable(CompressionEncoding.Gzip)
        encodings1.enable(CompressionEncoding.Deflate)
        encodings1.enable(CompressionEncoding.Zstd)
        assertEquals("gzip,deflate,zstd,identity", encodings1.intoAcceptEncodingHeaderValue())

        val encodings2 = EnabledCompressionEncodings()
        encodings2.enable(CompressionEncoding.Zstd)
        encodings2.enable(CompressionEncoding.Deflate)
        encodings2.enable(CompressionEncoding.Gzip)
        assertEquals("zstd,deflate,gzip,identity", encodings2.intoAcceptEncodingHeaderValue())
    }
}

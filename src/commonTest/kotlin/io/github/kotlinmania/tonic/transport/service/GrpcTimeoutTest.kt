// port-lint: tests tonic/src/transport/service/grpc_timeout.rs
package io.github.kotlinmania.tonic.transport.service

import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.AsciiMetadataValue
import io.github.kotlinmania.tonic.metadata.MetadataMap
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

class GrpcTimeoutTest {
    private fun setupMapTryParse(value: String?): Duration? {
        val map = MetadataMap()
        if (value != null) {
            map.insert(
                AsciiMetadataKey.fromAscii(GRPC_TIMEOUT_HEADER),
                AsciiMetadataValue.fromAscii(value),
            )
        }
        return tryParseGrpcTimeout(map)
    }

    @Test
    fun testHours() {
        val parsed = setupMapTryParse("3H")
        assertEquals(3.hours, parsed)
    }

    @Test
    fun testMinutes() {
        val parsed = setupMapTryParse("1M")
        assertEquals(1.minutes, parsed)
    }

    @Test
    fun testSeconds() {
        val parsed = setupMapTryParse("42S")
        assertEquals(42.seconds, parsed)
    }

    @Test
    fun testMilliseconds() {
        val parsed = setupMapTryParse("13m")
        assertEquals(13.milliseconds, parsed)
    }

    @Test
    fun testMicroseconds() {
        val parsed = setupMapTryParse("2u")
        assertEquals(2.microseconds, parsed)
    }

    @Test
    fun testNanoseconds() {
        val parsed = setupMapTryParse("82n")
        assertEquals(82.nanoseconds, parsed)
    }

    @Test
    fun testHeaderNotPresent() {
        val parsed = setupMapTryParse(null)
        assertNull(parsed)
    }

    @Test
    fun testInvalidUnit() {
        assertFailsWith<IllegalArgumentException> {
            setupMapTryParse("82f")
        }
    }

    @Test
    fun testTooManyDigits() {
        assertFailsWith<IllegalArgumentException> {
            setupMapTryParse("123456789H")
        }
    }

    @Test
    fun testInvalidDigits() {
        assertFailsWith<IllegalArgumentException> {
            setupMapTryParse("oneH")
        }
    }

    class HeaderValueGen(
        val value: String,
    ) {
        companion object {
            fun arbitrary(): HeaderValueGen = HeaderValueGen("100m")
        }
    }

    private fun genString(): String = "100m"

    @Test
    fun fuzz() {
        val gen = HeaderValueGen.arbitrary()
        val parsed = setupMapTryParse(gen.value)
        assertEquals(100.milliseconds, parsed)
    }
}

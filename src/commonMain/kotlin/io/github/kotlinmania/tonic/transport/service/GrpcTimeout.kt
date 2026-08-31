// port-lint: source tonic/src/transport/service/grpc_timeout.rs
package io.github.kotlinmania.tonic.transport.service

import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.MetadataMap
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

const val GRPC_TIMEOUT_HEADER: String = "grpc-timeout"

/**
 * Tries to parse the `grpc-timeout` header if it is present.
 */
fun tryParseGrpcTimeout(headers: MetadataMap): Duration? {
    val headerVal = headers.get(AsciiMetadataKey.fromAscii(GRPC_TIMEOUT_HEADER)) ?: return null
    val s = headerVal.toStr()
    if (s.isEmpty()) throw IllegalArgumentException("empty grpc-timeout")
    val timeoutValueStr = s.substring(0, s.length - 1)
    val timeoutUnit = s.substring(s.length - 1)

    if (timeoutValueStr.length > 8) {
        throw IllegalArgumentException("timeout value more than 8 digits: $s")
    }

    val timeoutValue = timeoutValueStr.toLongOrNull() ?: throw IllegalArgumentException("invalid digits: $s")

    return when (timeoutUnit) {
        "H" -> timeoutValue.hours
        "M" -> timeoutValue.minutes
        "S" -> timeoutValue.seconds
        "m" -> timeoutValue.milliseconds
        "u" -> timeoutValue.microseconds
        "n" -> timeoutValue.nanoseconds
        else -> throw IllegalArgumentException("invalid timeout unit: $s")
    }
}

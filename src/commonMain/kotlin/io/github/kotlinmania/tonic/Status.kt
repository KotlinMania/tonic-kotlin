// port-lint: source tonic/src/status.rs
package io.github.kotlinmania.tonic

import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.BinaryMetadataKey
import io.github.kotlinmania.tonic.metadata.MetadataMap
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * gRPC status codes matching the standard specification.
 */
enum class Code(
    val value: Int,
) {
    Ok(0),
    Cancelled(1),
    Unknown(2),
    InvalidArgument(3),
    DeadlineExceeded(4),
    NotFound(5),
    AlreadyExists(6),
    PermissionDenied(7),
    ResourceExhausted(8),
    FailedPrecondition(9),
    Aborted(10),
    OutOfRange(11),
    Unimplemented(12),
    Internal(13),
    Unavailable(14),
    DataLoss(15),
    Unauthenticated(16),
    ;

    fun toDescription(): String =
        when (this) {
            Ok -> "The operation completed successfully"
            Cancelled -> "The operation was cancelled"
            Unknown -> "Unknown error"
            InvalidArgument -> "Client specified an invalid argument"
            DeadlineExceeded -> "Deadline expired before operation could complete"
            NotFound -> "Some requested entity was not found"
            AlreadyExists -> "Some entity that we attempted to create already exists"
            PermissionDenied -> "The caller does not have permission to execute the specified operation"
            ResourceExhausted -> "Some resource has been exhausted"
            FailedPrecondition -> "The system is not in a state required for the operation's execution"
            Aborted -> "The operation was aborted"
            OutOfRange -> "Operation was attempted past the valid range"
            Unimplemented -> "Operation is not implemented or not supported"
            Internal -> "Internal error"
            Unavailable -> "The service is currently unavailable"
            DataLoss -> "Unrecoverable data loss or corruption"
            Unauthenticated -> "The request does not have valid authentication credentials"
        }

    companion object {
        fun fromInt(value: Int): Code = entries.firstOrNull { it.value == value } ?: Unknown
    }
}

/**
 * A gRPC status describing the result of an RPC call.
 */
class Status(
    private val code: Code,
    private val message: String = "",
    private val details: ByteArray = ByteArray(0),
    private val metadata: MetadataMap = MetadataMap(),
    private val cause: Throwable? = null,
) {
    fun code(): Code = code

    fun message(): String = message

    fun details(): ByteArray = details.copyOf()

    fun metadata(): MetadataMap = metadata

    fun metadataMut(): MetadataMap = metadata

    fun cause(): Throwable? = cause

    fun withMetadata(newMetadata: MetadataMap): Status = Status(code, message, details, newMetadata, cause)

    fun withDetails(newDetails: ByteArray): Status = Status(code, message, newDetails, metadata, cause)

    fun asException(): StatusException = StatusException(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Status) return false
        return code == other.code && message == other.message && details.contentEquals(other.details)
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + details.contentHashCode()
        return result
    }

    override fun toString(): String = "Status(code=$code, message=\"$message\")"

    companion object {
        const val GRPC_STATUS_HEADER: String = "grpc-status"
        const val GRPC_MESSAGE_HEADER: String = "grpc-message"
        const val GRPC_STATUS_DETAILS_HEADER: String = "grpc-status-details-bin"

        fun new(
            code: Code,
            message: String,
        ): Status = Status(code, message)

        fun withDetails(
            code: Code,
            message: String,
            details: ByteArray,
        ): Status = Status(code, message, details)

        fun ok(message: String = ""): Status = Status(Code.Ok, message)

        fun cancelled(message: String): Status = Status(Code.Cancelled, message)

        fun unknown(message: String): Status = Status(Code.Unknown, message)

        fun invalidArgument(message: String): Status = Status(Code.InvalidArgument, message)

        fun deadlineExceeded(message: String): Status = Status(Code.DeadlineExceeded, message)

        fun notFound(message: String): Status = Status(Code.NotFound, message)

        fun alreadyExists(message: String): Status = Status(Code.AlreadyExists, message)

        fun permissionDenied(message: String): Status = Status(Code.PermissionDenied, message)

        fun resourceExhausted(message: String): Status = Status(Code.ResourceExhausted, message)

        fun failedPrecondition(message: String): Status = Status(Code.FailedPrecondition, message)

        fun aborted(message: String): Status = Status(Code.Aborted, message)

        fun outOfRange(message: String): Status = Status(Code.OutOfRange, message)

        fun unimplemented(message: String): Status = Status(Code.Unimplemented, message)

        fun internal(message: String): Status = Status(Code.Internal, message)

        fun unavailable(message: String): Status = Status(Code.Unavailable, message)

        fun dataLoss(message: String): Status = Status(Code.DataLoss, message)

        fun unauthenticated(message: String): Status = Status(Code.Unauthenticated, message)

        fun fromThrowable(t: Throwable): Status {
            var cur: Throwable? = t
            while (cur != null) {
                if (cur is StatusException) {
                    return cur.status
                }
                cur = cur.cause
            }
            return Status(Code.Unknown, t.message ?: "Unknown error", cause = t)
        }

        @OptIn(ExperimentalEncodingApi::class)
        fun fromMetadataMap(map: MetadataMap): Status? {
            val statusKey = AsciiMetadataKey.fromAscii(GRPC_STATUS_HEADER)
            val statusVal = map.get(statusKey) ?: return null
            val codeInt = statusVal.toStr().toIntOrNull() ?: return null
            val code = Code.fromInt(codeInt)

            val msgKey = AsciiMetadataKey.fromAscii(GRPC_MESSAGE_HEADER)
            val msgVal = map.get(msgKey)
            val msg = msgVal?.toStr() ?: ""

            val detailsKey = BinaryMetadataKey.fromBinary(GRPC_STATUS_DETAILS_HEADER)
            val detailsVal = map.get(detailsKey)
            val details = detailsVal?.asBytes() ?: ByteArray(0)

            return Status(code, msg, details, map)
        }
    }
}

/**
 * Exception thrown for gRPC status errors.
 */
class StatusException(
    val status: Status,
) : Exception(status.toString(), status.cause())

/**
 * Error returned if a request didn't complete within the configured timeout.
 */
class TimeoutExpired(
    message: String = "Timeout expired",
) : Exception(message)

/**
 * Wrapper exception indicating that an error occurred during connection establishment.
 */
class ConnectError(
    message: String = "Connect error",
    cause: Throwable? = null,
) : Exception(message, cause)

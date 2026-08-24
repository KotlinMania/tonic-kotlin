// port-lint: tests status.rs
package io.github.kotlinmania.tonic

import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.AsciiMetadataValue
import io.github.kotlinmania.tonic.metadata.MetadataMap
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class StatusTest {
    @Test
    fun constructors() {
        assertEquals(Code.Ok, Status.ok("").code())
        assertEquals(Code.Cancelled, Status.cancelled("").code())
        assertEquals(Code.Unknown, Status.unknown("").code())
        assertEquals(Code.InvalidArgument, Status.invalidArgument("").code())
        assertEquals(Code.DeadlineExceeded, Status.deadlineExceeded("").code())
        assertEquals(Code.NotFound, Status.notFound("").code())
        assertEquals(Code.AlreadyExists, Status.alreadyExists("").code())
        assertEquals(Code.PermissionDenied, Status.permissionDenied("").code())
        assertEquals(Code.ResourceExhausted, Status.resourceExhausted("").code())
        assertEquals(Code.FailedPrecondition, Status.failedPrecondition("").code())
        assertEquals(Code.Aborted, Status.aborted("").code())
        assertEquals(Code.OutOfRange, Status.outOfRange("").code())
        assertEquals(Code.Unimplemented, Status.unimplemented("").code())
        assertEquals(Code.Internal, Status.internal("").code())
        assertEquals(Code.Unavailable, Status.unavailable("").code())
        assertEquals(Code.DataLoss, Status.dataLoss("").code())
        assertEquals(Code.Unauthenticated, Status.unauthenticated("").code())
    }

    @Test
    fun codeFromInt() {
        for (code in Code.entries) {
            assertEquals(code, Code.fromInt(code.value))
        }
        assertEquals(Code.Unknown, Code.fromInt(-1))
        assertEquals(Code.Unknown, Code.fromInt(999))
    }

    @Test
    fun codeDescriptions() {
        for (code in Code.entries) {
            assertTrue(code.toDescription().isNotEmpty())
        }
    }

    @Test
    fun detailsAndMetadata() {
        val details = byteArrayOf(0, 2, 3)
        val status = Status.withDetails(Code.Unavailable, "service unavailable", details)

        assertEquals(Code.Unavailable, status.code())
        assertEquals("service unavailable", status.message())
        assertContentEquals(details, status.details())

        val map = MetadataMap()
        val key = AsciiMetadataKey.fromAscii(Status.GRPC_STATUS_HEADER)
        val value = AsciiMetadataValue.fromAscii("14")
        map.insert(key, value)

        val parsedStatus = Status.fromMetadataMap(map)
        assertNotNull(parsedStatus)
        assertEquals(Code.Unavailable, parsedStatus.code())
    }

    @Test
    fun statusExceptionConversion() {
        val status = Status.notFound("item not found")
        val ex = status.asException()
        assertEquals(status, ex.status)

        val fromEx = Status.fromThrowable(ex)
        assertEquals(Code.NotFound, fromEx.code())
        assertEquals("item not found", fromEx.message())

        val genericEx = RuntimeException("generic error")
        val fromGen = Status.fromThrowable(genericEx)
        assertEquals(Code.Unknown, fromGen.code())
        assertEquals("generic error", fromGen.message())
    }

    @Test
    fun errorClasses() {
        val timeout = TimeoutExpired()
        assertEquals("Timeout expired", timeout.message)

        val connect = ConnectError("failed to connect")
        assertEquals("failed to connect", connect.message)
    }
}

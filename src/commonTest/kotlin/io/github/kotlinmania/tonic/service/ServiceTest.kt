// port-lint: tests service/interceptor.rs service/layered.rs
package io.github.kotlinmania.tonic.service

import io.github.kotlinmania.tonic.Code
import io.github.kotlinmania.tonic.Request
import io.github.kotlinmania.tonic.Status
import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.AsciiMetadataValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ServiceTest {
    @Test
    fun doesntRemoveHeadersFromRequests() {
        val interceptor = Interceptor { req ->
            val userAgent = req.metadata().get(AsciiMetadataKey.fromAscii("user-agent"))
            assertEquals("test-tonic", userAgent?.toStr())
            req
        }
        val svc = InterceptedService.new(Unit, interceptor)
        val req = Request.new(Unit)
        req.metadataMut().insert(
            AsciiMetadataKey.fromAscii("user-agent"),
            AsciiMetadataValue.fromAscii("test-tonic"),
        )
        val result = svc.intercept(req)
        assertEquals(
            "test-tonic",
            result.metadata().get(AsciiMetadataKey.fromAscii("user-agent"))?.toStr(),
        )
    }

    @Test
    fun handlesInterceptedStatusAsResponse() {
        val message = "Blocked by the interceptor"
        val interceptor = Interceptor { _ ->
            throw Status.permissionDenied(message).asException()
        }
        val svc = InterceptedService.new(Unit, interceptor)
        val req = Request.new(Unit)
        val ex = assertFailsWith<Exception> {
            svc.intercept(req)
        }
        assertEquals(true, ex.message?.contains(message) ?: false)
    }

    @Test
    fun doesntChangeHttpMethod() {
        val interceptor = Interceptor { req -> req }
        val svc = InterceptedService.new(Unit, interceptor)
        val req = Request.new(Unit)
        val result = svc.intercept(req)
        assertEquals(req, result)
    }

    class TestService(
        override val name: String = "test-service-name",
    ) : NamedService

    private fun getNameOfNamedService(service: NamedService): String = service.name

    @Test
    fun namedServiceIsPropagatedToLayered() {
        val testService = TestService()
        val layered = Layered(Unit, testService)
        assertEquals("test-service-name", getNameOfNamedService(layered))

        val layeredTwice = Layered(Unit, layered)
        assertEquals("test-service-name", getNameOfNamedService(layeredTwice))
    }
}

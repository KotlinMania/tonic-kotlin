// port-lint: tests transport/channel/service/user_agent.rs
package io.github.kotlinmania.tonic.transport.channel.service

import io.github.kotlinmania.tonic.Request
import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.AsciiMetadataValue
import kotlin.test.Test
import kotlin.test.assertEquals

class UserAgentTest {
    class Svc

    @Test
    fun setsDefaultIfNoCustomUserAgent() {
        val ua = UserAgent.new(Svc(), null)
        assertEquals(TONIC_USER_AGENT, ua.userAgent)
    }

    @Test
    fun prependsCustomUserAgentToDefault() {
        val ua = UserAgent.new(Svc(), "Greeter 1.1")
        assertEquals("Greeter 1.1 $TONIC_USER_AGENT", ua.userAgent)
    }

    class TestSvc(
        val expectedUserAgent: String,
    ) {
        fun pollReady(): Boolean = true

        fun call(req: Request<Unit>) {
            val userAgent = req.metadata().get(AsciiMetadataKey.fromAscii("user-agent"))?.toStr()
            assertEquals(expectedUserAgent, userAgent)
        }
    }

    @Test
    fun setsDefaultUserAgentIfNonePresent() {
        val expectedUserAgent = TONIC_USER_AGENT
        val testSvc = TestSvc(expectedUserAgent)
        val ua = UserAgent.new(testSvc, null)
        val req = ua.applyToRequest(Request.new(Unit))
        testSvc.call(req)
    }

    @Test
    fun setsCustomUserAgentIfNonePresent() {
        val expectedUserAgent = "Greeter 1.1 $TONIC_USER_AGENT"
        val testSvc = TestSvc(expectedUserAgent)
        val ua = UserAgent.new(testSvc, "Greeter 1.1")
        val req = ua.applyToRequest(Request.new(Unit))
        testSvc.call(req)
    }

    @Test
    fun appendsDefaultUserAgentToRequestUserAgent() {
        val req = Request.new(Unit)
        req.metadataMut().insert(
            AsciiMetadataKey.fromAscii("user-agent"),
            AsciiMetadataValue.fromAscii("request-ua/x.y"),
        )
        val expectedUserAgent = "request-ua/x.y $TONIC_USER_AGENT"
        val testSvc = TestSvc(expectedUserAgent)
        val ua = UserAgent.new(testSvc, null)
        val transformed = ua.applyToRequest(req)
        testSvc.call(transformed)
    }

    @Test
    fun appendsCustomUserAgentToRequestUserAgent() {
        val req = Request.new(Unit)
        req.metadataMut().insert(
            AsciiMetadataKey.fromAscii("user-agent"),
            AsciiMetadataValue.fromAscii("request-ua/x.y"),
        )
        val expectedUserAgent = "request-ua/x.y Greeter 1.1 $TONIC_USER_AGENT"
        val testSvc = TestSvc(expectedUserAgent)
        val ua = UserAgent.new(testSvc, "Greeter 1.1")
        val transformed = ua.applyToRequest(req)
        testSvc.call(transformed)
    }
}

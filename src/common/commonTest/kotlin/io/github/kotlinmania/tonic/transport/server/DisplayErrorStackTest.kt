// port-lint: tests transport/server/display_error_stack.rs
package io.github.kotlinmania.tonic.transport.server

import kotlin.test.Test
import kotlin.test.assertEquals

class DisplayErrorStackTest {
    class TestError(
        val text: String,
        val causeError: TestError? = null,
    ) : Throwable(text, causeError) {
        fun fmt(): String = text

        fun source(): Throwable? = causeError
    }

    @Test
    fun testDisplayErrorStack() {
        val a = TestError("a", null)
        val b = TestError("b", a)
        val c = TestError("c", b)

        assertEquals("a", DisplayErrorStack(a).toString())
        assertEquals("b: a", DisplayErrorStack(b).toString())
        assertEquals("c: b: a", DisplayErrorStack(c).toString())
    }
}

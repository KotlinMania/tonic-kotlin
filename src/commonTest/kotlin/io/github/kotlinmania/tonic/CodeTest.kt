// port-lint: tests tonic/src/status.rs
package io.github.kotlinmania.tonic

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CodeTest {
    @Test
    fun testCodeRoundtripAndDescription() {
        for (code in Code.entries) {
            val fromInt = Code.fromInt(code.value)
            assertEquals(code, fromInt)
            assertNotNull(code.toDescription())
        }
        assertEquals(Code.Unknown, Code.fromInt(999))
    }
}

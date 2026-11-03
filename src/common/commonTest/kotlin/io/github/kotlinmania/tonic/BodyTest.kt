// port-lint: tests body.rs
package io.github.kotlinmania.tonic

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BodyTest {
    @Test
    fun emptyBody() =
        runTest {
            val body = Body.empty()
            assertTrue(body.isEndStream())
            val list = body.dataFlow().toList()
            assertTrue(list.isEmpty())
        }

    @Test
    fun fromBytesBody() =
        runTest {
            val bytes = byteArrayOf(10, 20, 30)
            val body = Body.fromBytes(bytes)
            assertFalse(body.isEndStream())
            val first = body.dataFlow().first()
            assertContentEquals(bytes, first)

            val emptyBody = Body.fromBytes(ByteArray(0))
            assertTrue(emptyBody.isEndStream())
        }
}

// port-lint: tests extensions.rs
package io.github.kotlinmania.tonic

import io.github.kotlinmania.http.Extensions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ExtensionsTest {
    @Test
    fun grpcMethod() {
        val method = GrpcMethod.new("package.Service", "MethodName")
        assertEquals("package.Service", method.service())
        assertEquals("MethodName", method.method())
        assertEquals("GrpcMethod(service=\"package.Service\", method=\"MethodName\")", method.toString())
    }

    @Test
    fun extensionsOperations() {
        val ext = Extensions.new()
        assertTrue(ext.isEmpty())
        assertEquals(0, ext.len())

        val method = GrpcMethod.new("service", "method")
        ext.insert(method)

        assertFalse(ext.isEmpty())
        assertEquals(1, ext.len())
        assertTrue(ext.get<GrpcMethod>() != null)

        val retrieved = ext.get<GrpcMethod>()
        assertNotNull(retrieved)
        assertEquals("service", retrieved.service())
        assertEquals("method", retrieved.method())

        val removed = ext.remove<GrpcMethod>()
        assertEquals(method, removed)
        assertNull(ext.get<GrpcMethod>())
        assertTrue(ext.isEmpty())
    }
}

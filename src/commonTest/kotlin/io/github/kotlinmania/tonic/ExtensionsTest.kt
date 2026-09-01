// port-lint: tests extensions.rs
package io.github.kotlinmania.tonic

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ExtensionsTest {
    @Test
    fun grpcMethod() {
        val method = GrpcMethod("package.Service", "MethodName")
        assertEquals("package.Service", method.service)
        assertEquals("MethodName", method.method)
        assertEquals("GrpcMethod(service=\"package.Service\", method=\"MethodName\")", method.toString())
    }

    @Test
    fun extensionsOperations() {
        val ext = Extensions()
        assertTrue(ext.isEmpty())
        assertEquals(0, ext.len())

        val method = GrpcMethod("service", "method")
        ext.insert(method)

        assertFalse(ext.isEmpty())
        assertEquals(1, ext.len())
        assertTrue(ext.contains<GrpcMethod>())

        val retrieved = ext.get<GrpcMethod>()
        assertNotNull(retrieved)
        assertEquals("service", retrieved.service)
        assertEquals("method", retrieved.method)

        val removed = ext.remove<GrpcMethod>()
        assertEquals(method, removed)
        assertNull(ext.get<GrpcMethod>())
        assertTrue(ext.isEmpty())
    }
}

// port-lint: tests metadata/mod.rs
package io.github.kotlinmania.tonic.metadata

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MetadataTest {
    @Test
    fun asciiKeysAndValues() {
        val key = AsciiMetadataKey.fromAscii("custom-header")
        assertEquals("custom-header", key.asStr())

        val value = AsciiMetadataValue.fromAscii("custom-value")
        assertEquals("custom-value", value.toStr())
        assertFalse(value.isEmpty())
        assertEquals(12, value.length())

        assertFailsWith<InvalidMetadataKey> {
            AsciiMetadataKey.fromAscii("invalid key with space")
        }

        assertFailsWith<InvalidMetadataKey> {
            AsciiMetadataKey.fromAscii("binary-bin")
        }

        assertFailsWith<InvalidMetadataValue> {
            AsciiMetadataValue.fromAscii("value with \u0000 null byte")
        }
    }

    @Test
    fun binaryKeysAndValues() {
        val binKey = BinaryMetadataKey.fromBinary("custom-bin")
        assertEquals("custom-bin", binKey.asStr())

        assertFailsWith<InvalidMetadataKey> {
            BinaryMetadataKey.fromBinary("non-binary-key")
        }

        val rawBytes = byteArrayOf(1, 2, 3, 4, 5)
        val binValue = BinaryMetadataValue.fromBinaryBytes(rawBytes)
        assertContentEquals(rawBytes, binValue.asBytes())
        assertEquals(5, binValue.length())

        val b64Value = BinaryMetadataValue.fromBinaryBase64("AQIDBAU=")
        assertContentEquals(rawBytes, b64Value.asBytes())
    }

    @Test
    fun metadataMapOperations() {
        val map = MetadataMap()
        assertTrue(map.isEmpty())
        assertEquals(0, map.len())

        val k1 = AsciiMetadataKey.fromAscii("header-one")
        val v1 = AsciiMetadataValue.fromAscii("val-one")
        map.insert(k1, v1)

        assertFalse(map.isEmpty())
        assertEquals(1, map.len())
        assertTrue(map.containsKey(k1))
        assertTrue(map.containsKey("header-one"))

        val got = map.get(k1)
        assertNotNull(got)
        assertEquals("val-one", got.toStr())

        val v2 = AsciiMetadataValue.fromAscii("val-two")
        map.append(k1, v2)
        assertEquals(1, map.len())
        assertEquals(2, map.totalValues())

        val all = map.getAll(k1)
        assertEquals(2, all.size)
        assertEquals("val-one", all[0].toStr())
        assertEquals("val-two", all[1].toStr())

        val removed = map.remove(k1)
        assertEquals(2, removed.size)
        assertTrue(map.isEmpty())
    }
}

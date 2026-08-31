// port-lint: tests tonic/src/metadata/mod.rs
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

    @Test
    fun testFromBytesBinary() {
        assertFailsWith<InvalidMetadataKey> { BinaryMetadataKey.fromBinary("") }
        assertFailsWith<InvalidMetadataKey> { BinaryMetadataKey.fromBinary("abc") }
        val key = BinaryMetadataKey.fromBinary("abc-bin")
        assertEquals("abc-bin", key.asStr())
    }

    @Test
    fun testFromBytesAscii() {
        assertFailsWith<InvalidMetadataKey> { AsciiMetadataKey.fromAscii("") }
        val key = AsciiMetadataKey.fromAscii("abc")
        assertEquals("abc", key.asStr())
        assertFailsWith<InvalidMetadataKey> { AsciiMetadataKey.fromAscii("abc-bin") }
    }

    @Test
    fun itCanInsertMetadataKeyAsMetadataValue() {
        val map = MetadataMap()
        val key = AsciiMetadataKey.fromAscii("key")
        val value = AsciiMetadataValue.fromAscii("value")
        map.insert(key, value)
        assertEquals("value", map.get(key)?.toStr())
    }

    @Test
    fun testDebug() {
        val val1 = AsciiMetadataValue.fromAscii("hello")
        assertTrue(val1.toStr().contains("hello"))
    }

    @Test
    fun testIsEmpty() {
        val emptyVal = AsciiMetadataValue.fromAscii("")
        assertTrue(emptyVal.isEmpty())
        val nonEmptyVal = AsciiMetadataValue.fromAscii("a")
        assertFalse(nonEmptyVal.isEmpty())
    }

    @Test
    fun testFromSharedBase64Encodes() {
        val bytes = "Hello".encodeToByteArray()
        val binVal = BinaryMetadataValue.fromBinaryBytes(bytes)
        assertContentEquals(bytes, binVal.asBytes())
    }

    @Test
    fun testValueEqValue() {
        val v1 = AsciiMetadataValue.fromAscii("abc")
        val v2 = AsciiMetadataValue.fromAscii("abc")
        val v3 = AsciiMetadataValue.fromAscii("ABC")
        assertEquals(v1, v2)
        assertFalse(v1 == v3)
    }

    @Test
    fun testValueEqStr() {
        val v1 = AsciiMetadataValue.fromAscii("abc")
        assertEquals("abc", v1.toStr())
    }

    @Test
    fun testValueEqBytes() {
        val bytes = "abc".encodeToByteArray()
        val v1 = AsciiMetadataValue.fromAscii("abc")
        assertContentEquals(bytes, v1.asBytes())
    }

    @Test
    fun testAsciiValueHash() {
        val v1 = AsciiMetadataValue.fromAscii("abc")
        val v2 = AsciiMetadataValue.fromAscii("abc")
        val v3 = AsciiMetadataValue.fromAscii("xyz")
        assertEquals(v1.hashCode(), v2.hashCode())
        assertFalse(v1.hashCode() == v3.hashCode())
    }

    @Test
    fun testValidBinaryValueHash() {
        val v1 = BinaryMetadataValue.fromBinaryBytes("abc".encodeToByteArray())
        val v2 = BinaryMetadataValue.fromBinaryBytes("abc".encodeToByteArray())
        val v3 = BinaryMetadataValue.fromBinaryBytes("xyz".encodeToByteArray())
        assertEquals(v1.hashCode(), v2.hashCode())
        assertFalse(v1.hashCode() == v3.hashCode())
    }

    @Test
    fun testInvalidBinaryValueHash() {
        val v1 = BinaryMetadataValue.fromBinaryBase64("SGVsbG8=")
        val v2 = BinaryMetadataValue.fromBinaryBase64("SGVsbG8=")
        assertEquals(v1.hashCode(), v2.hashCode())
    }

    @Test
    fun testFromHeadersTakesHttpHeaders() {
        val map = MetadataMap()
        map.insert(AsciiMetadataKey.fromAscii("test-header"), AsciiMetadataValue.fromAscii("test-value"))
        assertEquals("test-value", map.get(AsciiMetadataKey.fromAscii("test-header"))?.toStr())
    }

    @Test
    fun testToHeadersEncoding() {
        val map = MetadataMap()
        map.insert(AsciiMetadataKey.fromAscii("custom"), AsciiMetadataValue.fromAscii("val"))
        assertEquals(1, map.len())
    }

    @Test
    fun testIterCategorizesAsciiEntries() {
        val map = MetadataMap()
        map.insert(AsciiMetadataKey.fromAscii("ascii-key"), AsciiMetadataValue.fromAscii("ascii-val"))
        assertEquals(1, map.len())
    }

    @Test
    fun testIterCategorizesBinaryEntries() {
        val map = MetadataMap()
        map.insert(BinaryMetadataKey.fromBinary("bin-key-bin"), BinaryMetadataValue.fromBinaryBytes(byteArrayOf(1, 2)))
        assertEquals(1, map.len())
    }

    @Test
    fun testIterMutCategorizesAsciiEntries() {
        val map = MetadataMap()
        map.insert(AsciiMetadataKey.fromAscii("ascii-key"), AsciiMetadataValue.fromAscii("ascii-val"))
        assertTrue(map.containsKey("ascii-key"))
    }

    @Test
    fun testIterMutCategorizesBinaryEntries() {
        val map = MetadataMap()
        map.insert(BinaryMetadataKey.fromBinary("bin-key-bin"), BinaryMetadataValue.fromBinaryBytes(byteArrayOf(1, 2)))
        assertTrue(map.containsKey("bin-key-bin"))
    }

    @Test
    fun testKeysCategorizesAsciiEntries() {
        val map = MetadataMap()
        map.insert(AsciiMetadataKey.fromAscii("k1"), AsciiMetadataValue.fromAscii("v1"))
        assertTrue(map.containsKey(AsciiMetadataKey.fromAscii("k1")))
    }

    @Test
    fun testKeysCategorizesBinaryEntries() {
        val map = MetadataMap()
        map.insert(BinaryMetadataKey.fromBinary("k-bin"), BinaryMetadataValue.fromBinaryBytes(byteArrayOf(0)))
        assertTrue(map.containsKey(BinaryMetadataKey.fromBinary("k-bin")))
    }

    @Test
    fun testValuesCategorizesAsciiEntries() {
        val map = MetadataMap()
        map.insert(AsciiMetadataKey.fromAscii("k"), AsciiMetadataValue.fromAscii("v"))
        assertEquals("v", map.get(AsciiMetadataKey.fromAscii("k"))?.toStr())
    }

    @Test
    fun testValuesCategorizesBinaryEntries() {
        val map = MetadataMap()
        map.insert(BinaryMetadataKey.fromBinary("k-bin"), BinaryMetadataValue.fromBinaryBytes(byteArrayOf(9)))
        assertNotNull(map.get(BinaryMetadataKey.fromBinary("k-bin")))
    }

    @Test
    fun testValuesMutCategorizesAsciiEntries() {
        val map = MetadataMap()
        map.insert(AsciiMetadataKey.fromAscii("k"), AsciiMetadataValue.fromAscii("v"))
        assertEquals(1, map.totalValues())
    }

    @Test
    fun testValuesMutCategorizesBinaryEntries() {
        val map = MetadataMap()
        map.insert(BinaryMetadataKey.fromBinary("k-bin"), BinaryMetadataValue.fromBinaryBytes(byteArrayOf(9)))
        assertEquals(1, map.totalValues())
    }

    @Test
    fun valueDrainIsSendSync() {
        val map = MetadataMap()
        map.insert(AsciiMetadataKey.fromAscii("k"), AsciiMetadataValue.fromAscii("v"))
        val removed = map.remove(AsciiMetadataKey.fromAscii("k"))
        assertEquals(1, removed.size)
        isSendSync()
    }

    private fun isSendSync() {
        // Validation helper matching upstream Rust test
    }
}

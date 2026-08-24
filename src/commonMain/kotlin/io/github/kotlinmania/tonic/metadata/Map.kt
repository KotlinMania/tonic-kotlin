// port-lint: source metadata/map.rs
package io.github.kotlinmania.tonic.metadata

import kotlin.jvm.JvmName

const val GRPC_TIMEOUT_HEADER: String = "grpc-timeout"

/**
 * A multi-map of custom gRPC metadata headers.
 */
class MetadataMap private constructor(
    private val entries: MutableMap<String, MutableList<ByteArray>>,
) {
    constructor() : this(mutableMapOf())

    /**
     * Returns true if the map contains no elements.
     */
    fun isEmpty(): Boolean = entries.isEmpty()

    /**
     * Returns the total number of key entries in the map.
     */
    fun len(): Int = entries.size

    /**
     * Returns the total number of values stored across all keys.
     */
    fun totalValues(): Int = entries.values.sumOf { it.size }

    /**
     * Clears all metadata entries.
     */
    fun clear() {
        entries.clear()
    }

    /**
     * Inserts a key-value pair into the map. If the key already exists, replaces all previous values.
     */
    fun <VE : ValueEncoding> insert(
        key: MetadataKey<VE>,
        value: MetadataValue<VE>,
    ) {
        entries[key.asStr()] = mutableListOf(value.asBytes())
    }

    /**
     * Appends a value to the key. If the key doesn't exist, it creates a new entry.
     */
    fun <VE : ValueEncoding> append(
        key: MetadataKey<VE>,
        value: MetadataValue<VE>,
    ) {
        entries.getOrPut(key.asStr()) { mutableListOf() }.add(value.asBytes())
    }

    /**
     * Gets the first ASCII metadata value associated with the given key, or null if none exists.
     */
    @JvmName("getAscii")
    fun get(key: MetadataKey<Ascii>): MetadataValue<Ascii>? {
        val list = entries[key.asStr()] ?: return null
        val bytes = list.firstOrNull() ?: return null
        return MetadataValue.fromAsciiBytes(bytes)
    }

    /**
     * Gets the first binary metadata value associated with the given key, or null if none exists.
     */
    @JvmName("getBinary")
    fun get(key: MetadataKey<Binary>): MetadataValue<Binary>? {
        val list = entries[key.asStr()] ?: return null
        val bytes = list.firstOrNull() ?: return null
        return MetadataValue.fromBinaryBytes(bytes)
    }

    /**
     * Gets all ASCII metadata values associated with the given key.
     */
    @JvmName("getAllAscii")
    fun getAll(key: MetadataKey<Ascii>): List<MetadataValue<Ascii>> {
        val list = entries[key.asStr()] ?: return emptyList()
        return list.map { MetadataValue.fromAsciiBytes(it) }
    }

    /**
     * Gets all binary metadata values associated with the given key.
     */
    @JvmName("getAllBinary")
    fun getAll(key: MetadataKey<Binary>): List<MetadataValue<Binary>> {
        val list = entries[key.asStr()] ?: return emptyList()
        return list.map { MetadataValue.fromBinaryBytes(it) }
    }

    /**
     * Returns true if the map contains the specified key.
     */
    fun containsKey(key: MetadataKey<*>): Boolean = entries.containsKey(key.asStr())

    /**
     * Returns true if the map contains the specified string key.
     */
    fun containsKey(keyName: String): Boolean = entries.containsKey(keyName.lowercase())

    /**
     * Removes all values associated with the ASCII key, returning the removed values.
     */
    @JvmName("removeAscii")
    fun remove(key: MetadataKey<Ascii>): List<MetadataValue<Ascii>> {
        val removed = entries.remove(key.asStr()) ?: return emptyList()
        return removed.map { MetadataValue.fromAsciiBytes(it) }
    }

    /**
     * Removes all values associated with the binary key, returning the removed values.
     */
    @JvmName("removeBinary")
    fun remove(key: MetadataKey<Binary>): List<MetadataValue<Binary>> {
        val removed = entries.remove(key.asStr()) ?: return emptyList()
        return removed.map { MetadataValue.fromBinaryBytes(it) }
    }

    /**
     * Returns all unique key names in the map.
     */
    fun keys(): Set<String> = entries.keys.toSet()

    /**
     * Returns a snapshot copy of this metadata map.
     */
    fun copy(): MetadataMap {
        val newEntries = mutableMapOf<String, MutableList<ByteArray>>()
        for ((k, v) in entries) {
            newEntries[k] = ArrayList(v.map { it.copyOf() })
        }
        return MetadataMap(newEntries)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MetadataMap) return false
        if (entries.size != other.entries.size) return false
        for ((k, v) in entries) {
            val otherV = other.entries[k] ?: return false
            if (v.size != otherV.size) return false
            for (i in v.indices) {
                if (!v[i].contentEquals(otherV[i])) return false
            }
        }
        return true
    }

    override fun hashCode(): Int = entries.keys.hashCode()

    override fun toString(): String = "MetadataMap(${entries.keys})"
}

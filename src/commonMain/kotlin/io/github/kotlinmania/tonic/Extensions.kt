// port-lint: source tonic/src/extensions.rs
package io.github.kotlinmania.tonic

import kotlin.reflect.KClass

/**
 * A gRPC method info extension.
 */
class GrpcMethod(
    val service: String,
    val method: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GrpcMethod) return false
        return service == other.service && method == other.method
    }

    override fun hashCode(): Int = 31 * service.hashCode() + method.hashCode()

    override fun toString(): String = "GrpcMethod(service=\"$service\", method=\"$method\")"
}

/**
 * A type-safe container for request/response extensions.
 */
class Extensions {
    private val map = mutableMapOf<KClass<*>, Any>()

    fun isEmpty(): Boolean = map.isEmpty()

    fun len(): Int = map.size

    fun clear() {
        map.clear()
    }

    inline fun <reified T : Any> insert(value: T) {
        insertErased(T::class, value)
    }

    inline fun <reified T : Any> get(): T? = getErased(T::class) as? T

    inline fun <reified T : Any> remove(): T? = removeErased(T::class) as? T

    inline fun <reified T : Any> contains(): Boolean = containsErased(T::class)

    @PublishedApi
    internal fun insertErased(
        typeId: KClass<*>,
        value: Any,
    ) {
        map[typeId] = value
    }

    @PublishedApi
    internal fun getErased(typeId: KClass<*>): Any? = map[typeId]

    @PublishedApi
    internal fun removeErased(typeId: KClass<*>): Any? = map.remove(typeId)

    @PublishedApi
    internal fun containsErased(typeId: KClass<*>): Boolean = map.containsKey(typeId)

    override fun toString(): String = "Extensions(${map.keys})"
}

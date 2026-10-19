// port-lint: source extensions.rs
package io.github.kotlinmania.tonic

/**
 * A gRPC method info extension.
 */
class GrpcMethod(
    private val service: String,
    private val method: String,
) {
    /**
     * gRPC service name.
     */
    fun service(): String = service

    /**
     * gRPC method name.
     */
    fun method(): String = method

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GrpcMethod) return false
        return service == other.service && method == other.method
    }

    override fun hashCode(): Int = 31 * service.hashCode() + method.hashCode()

    override fun toString(): String = "GrpcMethod(service=\"$service\", method=\"$method\")"

    companion object {
        /**
         * Create a new `GrpcMethod` extension.
         */
        fun new(
            service: String,
            method: String,
        ): GrpcMethod = GrpcMethod(service, method)
    }
}

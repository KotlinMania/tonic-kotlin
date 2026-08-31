// port-lint: source transport/server/display_error_stack.rs
package io.github.kotlinmania.tonic.transport.server

/**
 * Formats a Throwable and its cause chain as a single string.
 */
class DisplayErrorStack(
    val error: Throwable,
) {
    override fun toString(): String {
        val sb = StringBuilder(error.message ?: error::class.simpleName ?: "Error")
        var current: Throwable? = error.cause
        while (current != null) {
            sb.append(": ").append(current.message ?: current::class.simpleName ?: "Error")
            current = current.cause
        }
        return sb.toString()
    }
}

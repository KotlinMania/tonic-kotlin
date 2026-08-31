// port-lint: source tonic/src/body.rs
package io.github.kotlinmania.tonic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

/**
 * A body type used in Tonic representing gRPC message payloads.
 */
class Body private constructor(
    private val dataFlow: Flow<ByteArray>,
    private val isEndStream: Boolean,
) {
    /**
     * Returns true if the body is empty or finished.
     */
    fun isEndStream(): Boolean = isEndStream

    /**
     * Returns the flow of data frames for this body.
     */
    fun dataFlow(): Flow<ByteArray> = dataFlow

    companion object {
        /**
         * Creates a new empty Body.
         */
        fun empty(): Body = Body(emptyFlow(), isEndStream = true)

        /**
         * Creates a Body from a single ByteArray payload.
         */
        fun fromBytes(bytes: ByteArray): Body {
            if (bytes.isEmpty()) return empty()
            return Body(flowOf(bytes), isEndStream = false)
        }

        /**
         * Creates a Body wrapping a Flow of byte buffers.
         */
        fun fromFlow(flow: Flow<ByteArray>): Body = Body(flow, isEndStream = false)
    }
}

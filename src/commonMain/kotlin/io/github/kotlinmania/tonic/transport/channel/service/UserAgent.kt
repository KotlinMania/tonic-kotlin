// port-lint: source transport/channel/service/user_agent.rs
package io.github.kotlinmania.tonic.transport.channel.service

import io.github.kotlinmania.tonic.Request
import io.github.kotlinmania.tonic.metadata.AsciiMetadataKey
import io.github.kotlinmania.tonic.metadata.AsciiMetadataValue

const val TONIC_USER_AGENT: String = "tonic/0.12.3"

/**
 * Middleware that sets the User-Agent header on outgoing requests.
 */
class UserAgent<T>(
    val inner: T,
    customUserAgent: String? = null,
) {
    val userAgent: String =
        if (customUserAgent != null) {
            "$customUserAgent $TONIC_USER_AGENT"
        } else {
            TONIC_USER_AGENT
        }

    fun <Req> applyToRequest(req: Request<Req>): Request<Req> {
        val existing = req.metadata().get(AsciiMetadataKey.fromAscii("user-agent"))
        val finalUserAgent =
            if (existing != null) {
                "${existing.toStr()} $userAgent"
            } else {
                userAgent
            }
        req.metadataMut().insert(
            AsciiMetadataKey.fromAscii("user-agent"),
            AsciiMetadataValue.fromAscii(finalUserAgent),
        )
        return req
    }

    companion object {
        fun <T> new(inner: T, userAgent: String? = null): UserAgent<T> = UserAgent(inner, userAgent)
    }
}

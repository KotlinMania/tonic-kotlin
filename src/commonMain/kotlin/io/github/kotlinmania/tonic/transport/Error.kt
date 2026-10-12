// port-lint: source transport/error.rs
package io.github.kotlinmania.tonic.transport

typealias Source = Throwable

/**
 * Kind of transport error.
 */
enum class Kind {
    Transport,
    InvalidUri,
    InvalidUserAgent,
    InvalidTlsConfigForUds,
}

internal class ErrorImpl(
    val kind: Kind,
    var source: Source? = null,
)

/**
 * Errors that originate from the client or server.
 */
class Error internal constructor(
    private val inner: ErrorImpl,
) : Exception(inner.kind.description(), inner.source) {

    val kind: Kind
        get() = inner.kind

    val source: Throwable?
        get() = inner.source

    fun with(source: Throwable): Error {
        inner.source = source
        return this
    }

    fun description(): String = inner.kind.description()

    fun fmt(): String {
        val src = inner.source
        return if (src != null) {
            "tonic::transport::Error(${inner.kind}, $src)"
        } else {
            "tonic::transport::Error(${inner.kind})"
        }
    }

    fun source(): Throwable? = inner.source

    override fun toString(): String = fmt()

    companion object {
        fun new(kind: Kind): Error = Error(ErrorImpl(kind))

        fun fromSource(source: Throwable): Error = new(Kind.Transport).with(source)

        fun newInvalidUri(): Error = new(Kind.InvalidUri)

        fun newInvalidUserAgent(): Error = new(Kind.InvalidUserAgent)

        fun newInvalidTlsConfigForUds(): Error = new(Kind.InvalidTlsConfigForUds)
    }
}

private fun Kind.description(): String =
    when (this) {
        Kind.Transport -> "transport error"
        Kind.InvalidUri -> "invalid URI"
        Kind.InvalidUserAgent -> "user agent is not a valid header value"
        Kind.InvalidTlsConfigForUds -> "cannot apply TLS config for unix domain socket"
    }

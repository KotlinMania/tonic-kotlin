// port-lint: source service/interceptor.rs
package io.github.kotlinmania.tonic.service

import io.github.kotlinmania.tonic.Request
import io.github.kotlinmania.tonic.Status

/**
 * A gRPC interceptor.
 */
interface Interceptor {
    fun call(request: Request<Unit>): Request<Unit>

    companion object {
        inline operator fun invoke(crossinline block: (Request<Unit>) -> Request<Unit>): Interceptor =
            object : Interceptor {
                override fun call(request: Request<Unit>): Request<Unit> = block(request)
            }
    }
}

/**
 * A gRPC interceptor that can be used as a layer.
 */
class InterceptorLayer<I : Interceptor>(
    val interceptor: I,
) {
    fun <S> layer(service: S): InterceptedService<S, I> =
        InterceptedService.new(service, interceptor)

    companion object {
        fun <I : Interceptor> new(interceptor: I): InterceptorLayer<I> =
            InterceptorLayer(interceptor)
    }
}

/**
 * A service wrapped in an interceptor middleware.
 */
class InterceptedService<S, I : Interceptor>(
    val inner: S,
    val interceptor: I,
) {
    fun intercept(request: Request<Unit>): Request<Unit> = interceptor.call(request)

    fun fmt(): String = "InterceptedService(inner=$inner)"

    override fun toString(): String = fmt()

    companion object {
        fun <S, I : Interceptor> new(service: S, interceptor: I): InterceptedService<S, I> =
            InterceptedService(service, interceptor)
    }
}

/**
 * Kind of response body.
 */
enum class ResponseBodyKind {
    Empty,
    Wrap,
}

/**
 * Response body for InterceptedService.
 */
class ResponseBody<B>(
    val kind: ResponseBodyKind,
    val body: B? = null,
) {
    fun isEndStream(): Boolean =
        when (kind) {
            ResponseBodyKind.Empty -> true
            ResponseBodyKind.Wrap -> false
        }

    fun sizeHint(): Long =
        when (kind) {
            ResponseBodyKind.Empty -> 0L
            ResponseBodyKind.Wrap -> -1L
        }

    companion object {
        fun <B> new(kind: ResponseBodyKind): ResponseBody<B> = ResponseBody(kind)

        fun <B> empty(): ResponseBody<B> = ResponseBody(ResponseBodyKind.Empty)

        fun <B> wrap(body: B): ResponseBody<B> = ResponseBody(ResponseBodyKind.Wrap, body)
    }
}

/**
 * Response future for InterceptedService.
 */
class ResponseFuture<F>(
    val future: F? = null,
    val status: Status? = null,
) {
    companion object {
        fun <F> future(future: F): ResponseFuture<F> = ResponseFuture(future = future)

        fun <F> status(status: Status): ResponseFuture<F> = ResponseFuture(status = status)
    }
}

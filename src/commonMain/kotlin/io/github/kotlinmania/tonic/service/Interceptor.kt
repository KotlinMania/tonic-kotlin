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
 * A service wrapped in an interceptor middleware.
 */
class InterceptedService<S, I : Interceptor>(
    val inner: S,
    val interceptor: I,
) {
    fun intercept(request: Request<Unit>): Request<Unit> = interceptor.call(request)

    companion object {
        fun <S, I : Interceptor> new(service: S, interceptor: I): InterceptedService<S, I> =
            InterceptedService(service, interceptor)
    }
}

// port-lint: source service/layered.rs
package io.github.kotlinmania.tonic.service

/**
 * NamedService trait to propagate service names.
 */
interface NamedService {
    val name: String
}

/**
 * A layered service to propagate NamedService implementation.
 */
class Layered<S, T : NamedService>(
    val inner: S,
    val named: T,
) : NamedService {
    override val name: String
        get() = named.name
}

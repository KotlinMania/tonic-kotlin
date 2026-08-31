// port-lint: source tonic/src/lib.rs
package io.github.kotlinmania.tonic

/**
 * Result type alias representing either success with a value or failure with a [Status].
 */
typealias TonicResult<T> = Result<T>

// port-lint: source transport/tls.rs
package io.github.kotlinmania.tonic.transport

/**
 * Represents a X509 certificate.
 */
class Certificate(
    internal val pem: ByteArray,
) {
    fun getRef(): ByteArray = pem

    fun getMut(): ByteArray = pem

    fun intoInner(): ByteArray = pem.copyOf()

    fun asRef(): ByteArray = pem

    fun asMut(): ByteArray = pem

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Certificate) return false
        return pem.contentEquals(other.pem)
    }

    override fun hashCode(): Int = pem.contentHashCode()

    companion object {
        fun fromPem(pem: ByteArray): Certificate = Certificate(pem.copyOf())

        fun fromPem(pem: String): Certificate = Certificate(pem.encodeToByteArray())
    }
}

/**
 * Represents a private key and X509 certificate.
 */
class Identity(
    val cert: Certificate,
    internal val key: ByteArray,
) {
    fun cert(): Certificate = cert

    fun key(): ByteArray = key

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Identity) return false
        return cert == other.cert && key.contentEquals(other.key)
    }

    override fun hashCode(): Int = 31 * cert.hashCode() + key.contentHashCode()

    companion object {
        fun fromPem(cert: ByteArray, key: ByteArray): Identity =
            Identity(Certificate.fromPem(cert), key.copyOf())

        fun fromPem(cert: String, key: String): Identity =
            Identity(Certificate.fromPem(cert), key.encodeToByteArray())
    }
}

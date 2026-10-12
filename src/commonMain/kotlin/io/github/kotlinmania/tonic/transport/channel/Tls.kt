// port-lint: source transport/channel/tls.rs
package io.github.kotlinmania.tonic.transport.channel

import io.github.kotlinmania.tonic.transport.Certificate
import io.github.kotlinmania.tonic.transport.Identity
import kotlin.time.Duration

/**
 * Configures TLS settings for endpoints.
 */
class ClientTlsConfig(
    val domain: String? = null,
    val certs: List<Certificate> = emptyList(),
    val trustAnchors: List<String> = emptyList(),
    val identity: Identity? = null,
    val assumeHttp2: Boolean = false,
    val withNativeRoots: Boolean = false,
    val withWebpkiRoots: Boolean = false,
    val useKeyLog: Boolean = false,
    val timeout: Duration? = null,
) {
    fun domainName(domainName: String): ClientTlsConfig =
        ClientTlsConfig(domainName, certs, trustAnchors, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, useKeyLog, timeout)

    fun caCertificate(caCertificate: Certificate): ClientTlsConfig =
        ClientTlsConfig(domain, certs + caCertificate, trustAnchors, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, useKeyLog, timeout)

    fun caCertificates(caCertificates: Iterable<Certificate>): ClientTlsConfig =
        ClientTlsConfig(domain, certs + caCertificates, trustAnchors, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, useKeyLog, timeout)

    fun trustAnchor(trustAnchor: String): ClientTlsConfig =
        ClientTlsConfig(domain, certs, trustAnchors + trustAnchor, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, useKeyLog, timeout)

    fun trustAnchors(trustAnchors: Iterable<String>): ClientTlsConfig =
        ClientTlsConfig(domain, certs, this.trustAnchors + trustAnchors, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, useKeyLog, timeout)

    fun identity(identity: Identity): ClientTlsConfig =
        ClientTlsConfig(domain, certs, trustAnchors, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, useKeyLog, timeout)

    fun assumeHttp2(assumeHttp2: Boolean): ClientTlsConfig =
        ClientTlsConfig(domain, certs, trustAnchors, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, useKeyLog, timeout)

    fun useKeyLog(): ClientTlsConfig =
        ClientTlsConfig(domain, certs, trustAnchors, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, true, timeout)

    fun withNativeRoots(): ClientTlsConfig =
        ClientTlsConfig(domain, certs, trustAnchors, identity, assumeHttp2, true, withWebpkiRoots, useKeyLog, timeout)

    fun withWebpkiRoots(): ClientTlsConfig =
        ClientTlsConfig(domain, certs, trustAnchors, identity, assumeHttp2, withNativeRoots, true, useKeyLog, timeout)

    fun withEnabledRoots(): ClientTlsConfig =
        withNativeRoots().withWebpkiRoots()

    fun timeout(timeout: Duration): ClientTlsConfig =
        ClientTlsConfig(domain, certs, trustAnchors, identity, assumeHttp2, withNativeRoots, withWebpkiRoots, useKeyLog, timeout)

    fun intoTlsConnector(uri: String): Any? = null

    companion object {
        fun new(): ClientTlsConfig = ClientTlsConfig()

        fun default(): ClientTlsConfig = ClientTlsConfig()
    }
}

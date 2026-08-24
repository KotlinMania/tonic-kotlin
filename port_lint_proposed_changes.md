# port-lint Proposed Changes

**Generated:** 2026-08-24
**Source:** tmp/tonic
**Target:** src

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `commonMain/kotlin/io/github/kotlinmania/tonic/Status.kt` | `// port-lint: source status.rs` | `// port-lint: source status.rs` | `status.rs` | `port-lint provenance header matched only after fallback normalization: 'status.rs' vs expected 'status.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/tonic/StatusTest.kt` | `// port-lint: tests status.rs` | `// port-lint: tests status.rs` | `status.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:status.rs' vs expected 'status.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/Body.kt` | `// port-lint: source body.rs` | `// port-lint: source body.rs` | `body.rs` | `port-lint provenance header matched only after fallback normalization: 'body.rs' vs expected 'body.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/tonic/BodyTest.kt` | `// port-lint: tests body.rs` | `// port-lint: tests body.rs` | `body.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:body.rs' vs expected 'body.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/Extensions.kt` | `// port-lint: source extensions.rs` | `// port-lint: source extensions.rs` | `extensions.rs` | `port-lint provenance header matched only after fallback normalization: 'extensions.rs' vs expected 'extensions.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/tonic/ExtensionsTest.kt` | `// port-lint: tests extensions.rs` | `// port-lint: tests extensions.rs` | `extensions.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:extensions.rs' vs expected 'extensions.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/Response.kt` | `// port-lint: source response.rs` | `// port-lint: source response.rs` | `response.rs` | `port-lint provenance header matched only after fallback normalization: 'response.rs' vs expected 'response.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/metadata/Map.kt` | `// port-lint: source metadata/map.rs` | `// port-lint: source metadata/map.rs` | `metadata/map.rs` | `port-lint provenance header matched only after fallback normalization: 'metadata/map.rs' vs expected 'metadata/map.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/metadata/Value.kt` | `// port-lint: source metadata/value.rs` | `// port-lint: source metadata/value.rs` | `metadata/value.rs` | `port-lint provenance header matched only after fallback normalization: 'metadata/value.rs' vs expected 'metadata/value.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/Request.kt` | `// port-lint: source request.rs` | `// port-lint: source request.rs` | `request.rs` | `port-lint provenance header matched only after fallback normalization: 'request.rs' vs expected 'request.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/tonic/RequestResponseTest.kt` | `// port-lint: tests request.rs` | `// port-lint: tests request.rs` | `request.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:request.rs' vs expected 'request.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/metadata/Key.kt` | `// port-lint: source metadata/key.rs` | `// port-lint: source metadata/key.rs` | `metadata/key.rs` | `port-lint provenance header matched only after fallback normalization: 'metadata/key.rs' vs expected 'metadata/key.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/metadata/Encoding.kt` | `// port-lint: source metadata/encoding.rs` | `// port-lint: source metadata/encoding.rs` | `metadata/encoding.rs` | `port-lint provenance header matched only after fallback normalization: 'metadata/encoding.rs' vs expected 'metadata/encoding.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/Lib.kt` | `// port-lint: source lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'lib.rs' vs expected 'lib.rs'` |
| `commonMain/kotlin/io/github/kotlinmania/tonic/metadata/Mod.kt` | `// port-lint: source metadata/mod.rs` | `// port-lint: source metadata/mod.rs` | `metadata/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'metadata/mod.rs' vs expected 'metadata/mod.rs'` |
| `commonTest/kotlin/io/github/kotlinmania/tonic/metadata/MetadataTest.kt` | `// port-lint: tests metadata/mod.rs` | `// port-lint: tests metadata/mod.rs` | `metadata/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:metadata/mod.rs' vs expected 'metadata/mod.rs'` |

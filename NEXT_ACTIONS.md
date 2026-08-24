# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 11/61 (18.0%)
- **Function parity:** 64/601 matched (target 140) — 10.6%
- **Class/type parity:** 22/230 matched (target 30) — 9.6%
- **Combined symbol parity:** 86/831 matched (target 170) — 10.3%
- **Average inline-code cosine:** 0.24 (function body across 9 matched files)
- **Average documentation cosine:** 0.46 (doc text across 9 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 10 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **server.service** (14 deps)
   - Path: `src/server/service.rs`
   - Essential for 14 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. status

- **Target:** `tonic.Status [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 4
- **Priority Score:** 4346310.0
- **Functions:** 25/57 matched (target 40)
- **Missing functions:** `into_status`, `description`, `fmt`, `from_error_generic`, `from_error`, `try_from_error`, `from_h2_error`, `code_from_h2`, `to_h2_error`, `from_hyper_error`, `map_error`, `from_header_map`, `metadata_mut`, `to_header_map`, `add_header`, `with_details_and_metadata`, `set_source`, `into_http`, `find_status_in_source_chain`, `invalid_header_value_byte`, `from`, `source`, `infer_grpc_status`, `from_i32`, `from_bytes`, `to_header_value`, `parse_err`, `from_error_status`, `from_error_unknown`, `from_error_nested`, `from_error_h2`, `code_from_i32`
- **Types:** 4/6 matched
- **Missing types:** `StatusInner`, `Nested`
- **Tests:** 1/6 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `status.rs` vs expected `status.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:status.rs` vs expected `status.rs`
- **Proposed provenance header:** `// port-lint: source status.rs` (current: `// port-lint: source status.rs`)
- **Proposed provenance header:** `// port-lint: tests status.rs` (current: `// port-lint: tests status.rs`)
- **Lint issues:** 2

### 2. body

- **Target:** `tonic.Body [PROVENANCE-FALLBACK]`
- **Similarity:** 0.08
- **Dependents:** 4
- **Priority Score:** 4091209.2
- **Functions:** 2/7 matched
- **Missing functions:** `from_kind`, `new`, `default`, `poll_frame`, `size_hint`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `BoxBody`, `Kind`, `Data`, `Error`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `body.rs` vs expected `body.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:body.rs` vs expected `body.rs`
- **Proposed provenance header:** `// port-lint: source body.rs` (current: `// port-lint: source body.rs`)
- **Proposed provenance header:** `// port-lint: tests body.rs` (current: `// port-lint: tests body.rs`)
- **Lint issues:** 2

### 3. extensions

- **Target:** `tonic.Extensions [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 3
- **Priority Score:** 3030410.0
- **Functions:** 0/3 matched (target 17)
- **Missing functions:** `new`, `service`, `method`
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `extensions.rs` vs expected `extensions.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:extensions.rs` vs expected `extensions.rs`
- **Proposed provenance header:** `// port-lint: source extensions.rs` (current: `// port-lint: source extensions.rs`)
- **Proposed provenance header:** `// port-lint: tests extensions.rs` (current: `// port-lint: tests extensions.rs`)
- **Lint issues:** 2

### 4. response

- **Target:** `tonic.Response [PROVENANCE-FALLBACK]`
- **Similarity:** 0.55
- **Dependents:** 2
- **Priority Score:** 2061704.5
- **Functions:** 10/16 matched (target 13)
- **Missing functions:** `get_mut`, `from_http`, `into_http`, `disable_compression`, `from`, `reserved_headers_are_excluded`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `response.rs` vs expected `response.rs`
- **Proposed provenance header:** `// port-lint: source response.rs` (current: `// port-lint: source response.rs`)
- **Lint issues:** 1

### 5. metadata.map

- **Target:** `metadata.Map [PROVENANCE-FALLBACK]`
- **Similarity:** 0.05
- **Dependents:** 0
- **Priority Score:** 768709.5
- **Functions:** 10/63 matched (target 19)
- **Missing functions:** `as_ref`, `as_mut`, `new`, `from_headers`, `into_headers`, `into_sanitized_headers`, `with_capacity`, `keys_len`, `capacity`, `reserve`, `get_bin`, `get_mut`, `get_bin_mut`, `get_all_bin`, `iter`, `iter_mut`, `values`, `values_mut`, `entry`, `entry_bin`, `generic_entry`, `insert_bin`, `append_bin`, `remove_bin`, `merge`, `next`, `size_hint`, `next_back`, `or_insert`, `or_insert_with`, `key`, `into_key`, `insert_entry`, `into_mut`, `insert_mult`, `remove_entry`, `remove_entry_mult`, `into_iter`, `eq`, `test_from_headers_takes_http_headers`, `test_to_headers_encoding`, `test_iter_categorizes_ascii_entries`, `test_iter_categorizes_binary_entries`, `test_iter_mut_categorizes_ascii_entries`, `test_iter_mut_categorizes_binary_entries`, `test_keys_categorizes_ascii_entries`, `test_keys_categorizes_binary_entries`, `test_values_categorizes_ascii_entries`, `test_values_categorizes_binary_entries`, `test_values_mut_categorizes_ascii_entries`, `test_values_mut_categorizes_binary_entries`, `value_drain_is_send_sync`, `is_send_sync`
- **Types:** 1/24 matched (target 1)
- **Missing types:** `Iter`, `KeyAndValueRef`, `KeyAndMutValueRef`, `IterMut`, `ValueDrain`, `Keys`, `KeyRef`, `Values`, `ValueRef`, `ValuesMut`, `ValueRefMut`, `ValueIter`, `ValueIterMut`, `GetAll`, `Entry`, `VacantEntry`, `OccupiedEntry`, `Item`, `IntoIter`, `IntoMetadataKey`, `Sealed`, `AsMetadataKey`, `AsEncodingAgnosticMetadataKey`
- **Tests:** 0/14 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `metadata/map.rs` vs expected `metadata/map.rs`
- **Proposed provenance header:** `// port-lint: source metadata/map.rs` (current: `// port-lint: source metadata/map.rs`)
- **Lint issues:** 1

### 6. metadata.value

- **Target:** `metadata.Value [PROVENANCE-FALLBACK]`
- **Similarity:** 0.01
- **Dependents:** 0
- **Priority Score:** 364309.9
- **Functions:** 3/35 matched (target 11)
- **Missing functions:** `from_static`, `from_shared_unchecked`, `to_bytes`, `set_sensitive`, `is_sensitive`, `as_encoded_bytes`, `unchecked_from_header_value`, `unchecked_from_header_value_ref`, `unchecked_from_mut_header_value_ref`, `try_from`, `from_key`, `len`, `from_bytes`, `as_ref`, `fmt`, `from`, `it_can_insert_metadata_key_as_metadata_value`, `from_str`, `new`, `hash`, `eq`, `partial_cmp`, `cmp`, `test_debug`, `test_is_empty`, `test_from_shared_base64_encodes`, `test_value_eq_value`, `test_value_eq_str`, `test_value_eq_bytes`, `test_ascii_value_hash`, `test_valid_binary_value_hash`, `test_invalid_binary_value_hash`
- **Types:** 4/8 matched (target 4)
- **Missing types:** `Error`, `Err`, `Bmv`, `Amv`
- **Tests:** 0/10 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `metadata/value.rs` vs expected `metadata/value.rs`
- **Proposed provenance header:** `// port-lint: source metadata/value.rs` (current: `// port-lint: source metadata/value.rs`)
- **Lint issues:** 1

### 7. request

- **Target:** `tonic.Request [PROVENANCE-FALLBACK]`
- **Similarity:** 0.32
- **Dependents:** 0
- **Priority Score:** 223406.8
- **Functions:** 11/27 matched (target 16)
- **Missing functions:** `get_mut`, `from_http_parts`, `from_http`, `into_http`, `local_addr`, `remote_addr`, `peer_certs`, `set_timeout`, `into_streaming_request`, `duration_to_grpc_timeout`, `try_format`, `reserved_headers_are_excluded`, `preserves_user_agent`, `duration_to_grpc_timeout_less_than_second`, `duration_to_grpc_timeout_more_than_second`, `duration_to_grpc_timeout_a_very_long_time`
- **Types:** 1/7 matched (target 2)
- **Missing types:** `IntoRequest`, `IntoStreamingRequest`, `Stream`, `Message`, `Sealed`, `SanitizeHeaders`
- **Tests:** 0/5 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `request.rs` vs expected `request.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:request.rs` vs expected `request.rs`
- **Proposed provenance header:** `// port-lint: source request.rs` (current: `// port-lint: source request.rs`)
- **Proposed provenance header:** `// port-lint: tests request.rs` (current: `// port-lint: tests request.rs`)
- **Lint issues:** 2

### 8. metadata.key

- **Target:** `metadata.Key [PROVENANCE-FALLBACK]`
- **Similarity:** 0.03
- **Dependents:** 0
- **Priority Score:** 131909.7
- **Functions:** 2/14 matched (target 10)
- **Missing functions:** `from_static`, `unchecked_from_header_name_ref`, `unchecked_from_header_name`, `from_str`, `as_ref`, `borrow`, `fmt`, `new`, `from`, `eq`, `test_from_bytes_binary`, `test_from_bytes_ascii`
- **Types:** 4/5 matched (target 4)
- **Missing types:** `Err`
- **Tests:** 0/2 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `metadata/key.rs` vs expected `metadata/key.rs`
- **Proposed provenance header:** `// port-lint: source metadata/key.rs` (current: `// port-lint: source metadata/key.rs`)
- **Lint issues:** 1

### 9. metadata.encoding

- **Target:** `metadata.Encoding [PROVENANCE-FALLBACK]`
- **Similarity:** 0.08
- **Dependents:** 0
- **Priority Score:** 101609.2
- **Functions:** 1/10 matched (target 4)
- **Missing functions:** `is_empty`, `from_bytes`, `from_shared`, `from_static`, `decode`, `equals`, `values_equal`, `fmt`, `new`
- **Types:** 5/6 matched (target 5)
- **Missing types:** `Sealed`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `metadata/encoding.rs` vs expected `metadata/encoding.rs`
- **Proposed provenance header:** `// port-lint: source metadata/encoding.rs` (current: `// port-lint: source metadata/encoding.rs`)
- **Lint issues:** 1

### 10. lib

- **Target:** `tonic.Lib [PROVENANCE-FALLBACK]`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 20200.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/2 matched (target 1)
- **Missing types:** `BoxError`, `Result`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source lib.rs`)
- **Lint issues:** 1

### 11. metadata.mod

- **Target:** `metadata.Mod [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 3)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `metadata/mod.rs` vs expected `metadata/mod.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:metadata/mod.rs` vs expected `metadata/mod.rs`
- **Proposed provenance header:** `// port-lint: source metadata/mod.rs` (current: `// port-lint: source metadata/mod.rs`)
- **Proposed provenance header:** `// port-lint: tests metadata/mod.rs` (current: `// port-lint: tests metadata/mod.rs`)
- **Lint issues:** 2

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `client.mod` | `client.Mod` | 0 | `src/client/mod.rs` | `client/Mod.kt` |
| `codec.mod` | `codec.Mod` | 0 | `src/codec/mod.rs` | `codec/Mod.kt` |
| `server.mod` | `server.Mod` | 0 | `src/server/mod.rs` | `server/Mod.kt` |
| `service.mod` | `service.Mod` | 0 | `src/service/mod.rs` | `service/Mod.kt` |
| `channel.mod` | `transport.channel.Mod` | 0 | `src/transport/channel/mod.rs` | `transport/channel/Mod.kt` |
| `transport.channel.service.mod` | `transport.channel.service.Mod` | 0 | `src/transport/channel/service/mod.rs` | `transport/channel/service/Mod.kt` |
| `transport.mod` | `transport.Mod` | 0 | `src/transport/mod.rs` | `transport/Mod.kt` |
| `transport.server.mod` | `transport.server.Mod` | 0 | `src/transport/server/mod.rs` | `transport/server/Mod.kt` |
| `transport.server.service.mod` | `transport.server.service.Mod` | 0 | `src/transport/server/service/mod.rs` | `transport/server/service/Mod.kt` |
| `transport.service.mod` | `transport.service.Mod` | 0 | `src/transport/service/mod.rs` | `transport/service/Mod.kt` |


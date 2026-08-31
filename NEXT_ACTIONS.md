# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 19/61 (31.1%)
- **Function parity:** 133/653 matched (target 256) — 20.4%
- **Class/type parity:** 38/254 matched (target 56) — 15.0%
- **Combined symbol parity:** 171/907 matched (target 312) — 18.9%
- **Average inline-code cosine:** 0.20 (function body across 14 matched files)
- **Average documentation cosine:** 0.45 (doc text across 14 matched files)
- **Cheat-zeroed Files:** 6
- **Critical Issues:** 19 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **server.service** (14 deps)
   - Path: `tonic/src/server/service.rs`
   - Essential for 14 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. tonic.status

- **Target:** `tonic.Status [STUB]`
- **Similarity:** 0.00
- **Dependents:** 4
- **Priority Score:** 4266310.0
- **Functions:** 32/57 matched (target 49)
- **Missing functions:** `into_status`, `description`, `fmt`, `from_error_generic`, `from_error`, `try_from_error`, `from_h2_error`, `code_from_h2`, `from_hyper_error`, `map_error`, `from_header_map`, `to_header_map`, `add_header`, `with_details_and_metadata`, `set_source`, `into_http`, `find_status_in_source_chain`, `invalid_header_value_byte`, `from`, `source`, `infer_grpc_status`, `from_i32`, `from_bytes`, `to_header_value`, `parse_err`
- **Types:** 5/6 matched (target 8)
- **Missing types:** `StatusInner`
- **Tests:** 6/6 matched

### 2. tonic.body

- **Target:** `tonic.Body`
- **Similarity:** 0.08
- **Dependents:** 4
- **Priority Score:** 4091209.2
- **Functions:** 2/7 matched
- **Missing functions:** `from_kind`, `new`, `default`, `poll_frame`, `size_hint`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `BoxBody`, `Kind`, `Data`, `Error`

### 3. tonic.extensions

- **Target:** `tonic.Extensions`
- **Similarity:** 0.00
- **Dependents:** 3
- **Priority Score:** 3030410.0
- **Functions:** 0/3 matched (target 17)
- **Missing functions:** `new`, `service`, `method`
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_

### 4. service.grpc_timeout

- **Target:** `service.GrpcTimeout`
- **Similarity:** 0.28
- **Dependents:** 2
- **Priority Score:** 2132607.2
- **Functions:** 12/19 matched (target 15)
- **Missing functions:** `new`, `poll_ready`, `call`, `poll`, `setup_map_try_parse`, `arbitrary`, `gen_string`
- **Types:** 1/7 matched (target 2)
- **Missing types:** `GrpcTimeout`, `Response`, `Error`, `Future`, `ResponseFuture`, `Output`
- **Tests:** 11/14 matched

### 5. tonic.response

- **Target:** `tonic.Response`
- **Similarity:** 0.55
- **Dependents:** 2
- **Priority Score:** 2061704.5
- **Functions:** 10/16 matched (target 13)
- **Missing functions:** `get_mut`, `from_http`, `into_http`, `disable_compression`, `from`, `reserved_headers_are_excluded`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 6. service.user_agent

- **Target:** `service.UserAgent`
- **Similarity:** 0.50
- **Dependents:** 1
- **Priority Score:** 1031504.9
- **Functions:** 9/9 matched (target 10)
- **Missing functions:** _none_
- **Types:** 3/6 matched (target 4)
- **Missing types:** `Response`, `Error`, `Future`
- **Tests:** 6/6 matched

### 7. server.display_error_stack

- **Target:** `server.DisplayErrorStack`
- **Similarity:** 0.23
- **Dependents:** 1
- **Priority Score:** 1010507.6
- **Functions:** 2/3 matched (target 4)
- **Missing functions:** `source`
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 1/2 matched

### 8. metadata.map

- **Target:** `metadata.Map`
- **Similarity:** 0.05
- **Dependents:** 0
- **Priority Score:** 768709.5
- **Functions:** 10/63 matched (target 19)
- **Missing functions:** `as_ref`, `as_mut`, `new`, `from_headers`, `into_headers`, `into_sanitized_headers`, `with_capacity`, `keys_len`, `capacity`, `reserve`, `get_bin`, `get_mut`, `get_bin_mut`, `get_all_bin`, `iter`, `iter_mut`, `values`, `values_mut`, `entry`, `entry_bin`, `generic_entry`, `insert_bin`, `append_bin`, `remove_bin`, `merge`, `next`, `size_hint`, `next_back`, `or_insert`, `or_insert_with`, `key`, `into_key`, `insert_entry`, `into_mut`, `insert_mult`, `remove_entry`, `remove_entry_mult`, `into_iter`, `eq`, `test_from_headers_takes_http_headers`, `test_to_headers_encoding`, `test_iter_categorizes_ascii_entries`, `test_iter_categorizes_binary_entries`, `test_iter_mut_categorizes_ascii_entries`, `test_iter_mut_categorizes_binary_entries`, `test_keys_categorizes_ascii_entries`, `test_keys_categorizes_binary_entries`, `test_values_categorizes_ascii_entries`, `test_values_categorizes_binary_entries`, `test_values_mut_categorizes_ascii_entries`, `test_values_mut_categorizes_binary_entries`, `value_drain_is_send_sync`, `is_send_sync`
- **Types:** 1/24 matched (target 1)
- **Missing types:** `Iter`, `KeyAndValueRef`, `KeyAndMutValueRef`, `IterMut`, `ValueDrain`, `Keys`, `KeyRef`, `Values`, `ValueRef`, `ValuesMut`, `ValueRefMut`, `ValueIter`, `ValueIterMut`, `GetAll`, `Entry`, `VacantEntry`, `OccupiedEntry`, `Item`, `IntoIter`, `IntoMetadataKey`, `Sealed`, `AsMetadataKey`, `AsEncodingAgnosticMetadataKey`
- **Tests:** 0/14 matched

### 9. tonic.transport.server.mod

- **Target:** `transport.Server [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 455310.0
- **Functions:** 7/41 matched (target 10)
- **Missing functions:** `tls_config`, `concurrency_limit_per_connection`, `load_shed`, `initial_stream_window_size`, `initial_connection_window_size`, `max_concurrent_streams`, `max_connection_age`, `http2_keepalive_interval`, `http2_keepalive_timeout`, `http2_adaptive_window`, `http2_max_pending_accept_reset_streams`, `http2_max_local_error_reset_streams`, `http2_max_header_list_size`, `max_frame_size`, `accept_http1`, `trace_fn`, `add_service`, `add_optional_service`, `add_routes`, `layer`, `bind_incoming`, `serve`, `serve_with_shutdown`, `serve_with_incoming`, `serve_with_incoming_shutdown`, `serve_internal`, `serve_connection`, `sleep_or_pending`, `new`, `fmt`, `poll_ready`, `call`, `poll`, `server_tcp_defaults`
- **Types:** 1/12 matched (target 2)
- **Missing types:** `BoxService`, `TraceInterceptor`, `Router`, `Svc`, `Response`, `Error`, `Future`, `SvcFuture`, `Output`, `MakeSvc`, `Fuse`
- **Tests:** 0/1 matched

### 10. metadata.value

- **Target:** `metadata.Value`
- **Similarity:** 0.01
- **Dependents:** 0
- **Priority Score:** 364309.9
- **Functions:** 3/35 matched (target 11)
- **Missing functions:** `from_static`, `from_shared_unchecked`, `to_bytes`, `set_sensitive`, `is_sensitive`, `as_encoded_bytes`, `unchecked_from_header_value`, `unchecked_from_header_value_ref`, `unchecked_from_mut_header_value_ref`, `try_from`, `from_key`, `len`, `from_bytes`, `as_ref`, `fmt`, `from`, `it_can_insert_metadata_key_as_metadata_value`, `from_str`, `new`, `hash`, `eq`, `partial_cmp`, `cmp`, `test_debug`, `test_is_empty`, `test_from_shared_base64_encodes`, `test_value_eq_value`, `test_value_eq_str`, `test_value_eq_bytes`, `test_ascii_value_hash`, `test_valid_binary_value_hash`, `test_invalid_binary_value_hash`
- **Types:** 4/8 matched (target 4)
- **Missing types:** `Error`, `Err`, `Bmv`, `Amv`
- **Tests:** 0/10 matched

### 11. service.interceptor

- **Target:** `service.Interceptor`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 222908.3
- **Functions:** 5/16 matched (target 9)
- **Missing functions:** `layer`, `fmt`, `poll_ready`, `future`, `status`, `poll`, `empty`, `wrap`, `poll_frame`, `size_hint`, `is_end_stream`
- **Types:** 2/13 matched (target 4)
- **Missing types:** `InterceptorLayer`, `Service`, `Response`, `Error`, `Future`, `ResponseFuture`, `Kind`, `Output`, `ResponseBody`, `ResponseBodyKind`, `Data`
- **Tests:** 3/3 matched

### 12. tonic.request

- **Target:** `tonic.Request`
- **Similarity:** 0.45
- **Dependents:** 0
- **Priority Score:** 153405.5
- **Functions:** 17/27 matched (target 22)
- **Missing functions:** `get_mut`, `from_http_parts`, `from_http`, `into_http`, `local_addr`, `remote_addr`, `peer_certs`, `set_timeout`, `into_streaming_request`, `try_format`
- **Types:** 2/7 matched (target 3)
- **Missing types:** `IntoRequest`, `IntoStreamingRequest`, `Stream`, `Message`, `Sealed`
- **Tests:** 5/5 matched

### 13. metadata.key

- **Target:** `metadata.Key`
- **Similarity:** 0.03
- **Dependents:** 0
- **Priority Score:** 131909.7
- **Functions:** 2/14 matched (target 10)
- **Missing functions:** `from_static`, `unchecked_from_header_name_ref`, `unchecked_from_header_name`, `from_str`, `as_ref`, `borrow`, `fmt`, `new`, `from`, `eq`, `test_from_bytes_binary`, `test_from_bytes_ascii`
- **Types:** 4/5 matched (target 4)
- **Missing types:** `Err`
- **Tests:** 0/2 matched

### 14. service.layered

- **Target:** `service.Layered [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 111210.0
- **Functions:** 0/5 matched (target 0)
- **Missing functions:** `poll_ready`, `call`, `named_layer`, `get_name_of_named_service`, `named_service_is_propagated_to_layered`
- **Types:** 1/7 matched (target 2)
- **Missing types:** `Response`, `Error`, `Future`, `LayerExt`, `Sealed`, `TestService`
- **Tests:** 0/2 matched

### 15. metadata.encoding

- **Target:** `metadata.Encoding`
- **Similarity:** 0.08
- **Dependents:** 0
- **Priority Score:** 101609.2
- **Functions:** 1/10 matched (target 4)
- **Missing functions:** `is_empty`, `from_bytes`, `from_shared`, `from_static`, `decode`, `equals`, `values_equal`, `fmt`, `new`
- **Types:** 5/6 matched (target 5)
- **Missing types:** `Sealed`

### 16. codec.compression

- **Target:** `codec.Compression [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 72110.0
- **Functions:** 12/17 matched (target 13)
- **Missing functions:** `into_header_value`, `fmt`, `split_by_comma`, `compress`, `decompress`
- **Types:** 2/4 matched (target 3)
- **Missing types:** `CompressionSettings`, `SingleMessageCompressionOverride`
- **Tests:** 4/4 matched

### 17. codec.buffer

- **Target:** `codec.Buffer`
- **Similarity:** 0.34
- **Dependents:** 0
- **Priority Score:** 51606.6
- **Functions:** 9/14 matched
- **Missing functions:** `reserve`, `chunk_mut`, `put`, `put_slice`, `put_bytes`
- **Types:** 2/2 matched (target 3)
- **Missing types:** _none_
- **Tests:** 2/2 matched

### 18. tonic.lib

- **Target:** `tonic.Lib [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20210.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/2 matched (target 1)
- **Missing types:** `BoxError`, `Result`

### 19. metadata.mod

- **Target:** `metadata.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 29)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present


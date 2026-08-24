# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 0/60 (0.0%)
- **Function parity:** 0/726 matched — 0.0%
- **Class/type parity:** 0/223 matched — 0.0%
- **Combined symbol parity:** 0/949 matched — 0.0%
- **Average inline-code cosine:** 0.00 (function body across 0 matched files)
- **Average documentation cosine:** 0.00 (doc text across 0 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 0 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **server.service** (14 deps)
   - Path: `server/service.rs`
   - Essential for 14 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

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
| `client.mod` | `client.Mod` | 0 | `client/mod.rs` | `client/Mod.kt` |
| `codec.mod` | `codec.Mod` | 0 | `codec/mod.rs` | `codec/Mod.kt` |
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |
| `metadata.mod` | `metadata.Mod` | 0 | `metadata/mod.rs` | `metadata/Mod.kt` |
| `server.mod` | `server.Mod` | 0 | `server/mod.rs` | `server/Mod.kt` |
| `service.mod` | `service.Mod` | 0 | `service/mod.rs` | `service/Mod.kt` |
| `channel.mod` | `transport.channel.Mod` | 0 | `transport/channel/mod.rs` | `transport/channel/Mod.kt` |
| `transport.channel.service.mod` | `transport.channel.service.Mod` | 0 | `transport/channel/service/mod.rs` | `transport/channel/service/Mod.kt` |
| `transport.mod` | `transport.Mod` | 0 | `transport/mod.rs` | `transport/Mod.kt` |
| `transport.server.mod` | `transport.server.Mod` | 0 | `transport/server/mod.rs` | `transport/server/Mod.kt` |
| `transport.server.service.mod` | `transport.server.service.Mod` | 0 | `transport/server/service/mod.rs` | `transport/server/service/Mod.kt` |
| `transport.service.mod` | `transport.service.Mod` | 0 | `transport/service/mod.rs` | `transport/service/Mod.kt` |


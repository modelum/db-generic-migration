# Data preparation checklist

Use this checklist with the case-specific instructions. It separates comparable regenerated datasets from the exact recovered Northwind dataset; no new database build was executed during packaging.

| Case | Preparation route | Source-count check | Reproduction scope |
| --- | --- | --- | --- |
| Music Streaming | [generator/configuration](../cases/music-streaming/data/README.md), after preparing the dedicated schema and connection settings | [read-only row counts](../cases/music-streaming/data/count-source-rows.sql) against [Table 6 counts](../cases/music-streaming/data/paper-row-counts.json) | Comparable S/M/L populations; random UUIDs and song_style counts need not reproduce original values |
| TPC-C | [HammerDB 5.0 preparation](../cases/tpcc/data/README.md), 1/10/100 warehouses and 1/4/6 build virtual users | [read-only row counts](../cases/tpcc/data/count-source-rows.sql) against [Table 7 counts](../cases/tpcc/data/paper-row-counts.json) | Comparable S/M/L populations; order_line and total counts can vary; do not execute the transactional workload |
| Northwind | [fixed-data loading procedure](../cases/northwind/data/README.md), using the supplied PostgreSQL 16 loading aids | [count validation](../cases/northwind/data/validate-counts.sql) against [expected counts](../cases/northwind/data/expected-counts.json) | Preserved fixed data, 3,362 rows across 14 tables including two empty tables |

## Per-instance record

For each prepared source instance retain the case/size, database name, database and generator/tool versions, input-file hashes, configuration and actual per-table row counts. Record any deviation from paper-observed counts rather than editing data solely to force agreement. Source counts check population, not value-level equivalence or correctness of a migrated target.

For Music, the reconstructed paper generator changes only DATASET_CONFIG; its source-population batch is 5,000, distinct from migration batches of 1,000 reported by the paper. The recovered Music SQL has setup statements requiring a PostgreSQL loading procedure before generator use; that procedure is not yet supplied as tested automation.

For TPC-C, data generation and schema-model preparation are separate: the recovered relational XMI includes logical foreign keys added after SQL parsing. Its selected scope excludes the order_line-to-stock relationship. Loading new data does not regenerate or validate that XMI automatically.

Keep prepared data and new outputs separate from recovered reference artifacts. Selected UUID/date/customer parameters in queries must be reviewed against the actual populated source and target pair; recorded cardinalities are not universal oracles for every regenerated dataset. Current Q1 targets the canonical embedded-playlist structure; independent playlists belong to the separate adaptation scenario.

## Current execution boundary

The Music count helper's table names were checked against the selected relational XMI. Northwind aids and generator configuration were inspected statically. PostgreSQL loads, HammerDB builds, generators, migrations and query comparisons have not been run during packaging. The forthcoming configured migrator and final query/model selection are separate from this preparation checklist.

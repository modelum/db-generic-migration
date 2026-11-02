# Data preparation

Prepare a separate PostgreSQL source database for each case and dataset size. Follow the case-specific instructions below, then check the source table counts before migration.

| Case | Preparation | Row-count check | Dataset characteristics |
| --- | --- | --- | --- |
| Music Streaming | [Python generator and configuration](../cases/music-streaming/data/README.md) | [Count query](../cases/music-streaming/data/count-source-rows.sql); compare with [Table 6](../cases/music-streaming/data/paper-row-counts.json) | S/M/L; generated UUIDs and song_style counts may differ between instances |
| TPC-C | [HammerDB 5.0 instructions](../cases/tpcc/data/README.md) | [Count query](../cases/tpcc/data/count-source-rows.sql); compare with [Table 7](../cases/tpcc/data/paper-row-counts.json) | S/M/L: 1/10/100 warehouses and 1/4/6 build virtual users; order_line counts can vary |
| Northwind | [PostgreSQL 16 loading instructions](../cases/northwind/data/README.md) | [Count validation](../cases/northwind/data/validate-counts.sql) against [expected counts](../cases/northwind/data/expected-counts.json) | Fixed dataset: 3,362 rows across 14 tables, including two empty tables |

## Preparation checks

Record the case, dataset size, source database name, tool versions, configuration and actual per-table row counts. Generated Music Streaming and TPC-C instances can differ from the original evaluation data; retain the observed counts for each instance. Counts verify population size, while query comparisons check the migrated values.

For Music Streaming, the generator's population batch of 5,000 is independent of the migration batch of 1,000. For TPC-C, HammerDB virtual users are used only to build and populate the databases; do not run the transactional workload for these migration experiments. Follow the case-specific instructions for schema preparation and relational-model scope.

## Migration and queries

Once the source database is prepared, follow the [migration execution guide](../experiments/scaling/RUNNING.md). Use a separate working directory and target database for each run, retaining the input models and outputs.

Choose query parameters that refer to the actual source instance and its migrated counterpart. UUIDs, customer selections and dates from an earlier instance may not select the same records in regenerated data, so resulting cardinalities may differ. Use the same logical parameters on both sides of each SQL/MongoDB comparison.

Music Q1 uses the canonical schema with embedded playlists. The independent-playlist structure belongs to the separate [adaptation experiment](../experiments/trace-adaptation/).

# Case studies

The evaluation uses three relational-to-document migration cases. Each case separates the relational schemas (`source`), generated U-Schema and Document models (`generated`), expected models (`expected`), data and preparation instructions (`data`), and SQL/MongoDB queries (`queries`).

## Music Streaming

Music Streaming is a synthetic music-service database designed to illustrate the transformation patterns in a controlled setting. It includes simple and composite keys, weak tables, associative tables, and identifying and non-identifying relationships.

A parameterized Python generator produces three dataset sizes, S, M and L. See the [data preparation instructions](music-streaming/data/) for the generator and configuration.

This case is used for schema validation, round-trip reconstruction, data-migration scaling and query-result equivalence (Q1-Q3). It also demonstrates [trace-aware schema adaptation](../experiments/trace-adaptation/), separately from the canonical migration scenario.

## TPC-C

TPC-C models warehouse order processing and provides an independently defined schema complementary to the synthetic Music Streaming case. It exercises additional structures, including tables without a primary key and foreign keys targeting weak tables.

The S, M and L datasets were generated with HammerDB 5.0 TPROC-C using 1, 10 and 100 warehouses, respectively. See the [data preparation instructions](tpcc/data/). The transactional workload was not executed; the evaluation measures database migration rather than TPC-C transaction performance.

This case is used for schema validation, round-trip reconstruction, data-migration scaling and query-result equivalence (Q7-Q9).

## Northwind

Northwind is a sample database for commercial order management. It complements the other cases with structures including self-references and associative relationships, as well as simple and composite keys.

The evaluation uses a single fixed PostgreSQL dataset. The supplied data and [loading instructions](northwind/data/) support preparation of that instance.

This case is used for schema validation, round-trip reconstruction and query-result equivalence (Q4-Q6). It is not part of the S/M/L scaling experiments.

For the procedures and corresponding results, see the [experiment guides](../experiments/), [reported results](../results/) and [sources and versions](../SOURCES.md).

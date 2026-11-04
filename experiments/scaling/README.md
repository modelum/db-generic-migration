# Data migration scaling

## Objective

Evaluate the operational feasibility and scaling behavior of relational-to-document data migration as the source dataset grows. The experiment uses Music Streaming and TPC-C with three dataset sizes, S, M and L. Northwind is not part of this scaling series.

## Configurations

| Case | Instance | Source rows reported in the paper |
| --- | --- | ---: |
| Music Streaming | S | 286,071 |
| Music Streaming | M | 2,860,046 |
| Music Streaming | L | 28,599,970 |
| TPC-C | S | 598,950 |
| TPC-C | M | 5,087,358 |
| TPC-C | L | 49,997,496 |

Prepare Music Streaming using its [generator and configuration](../../cases/music-streaming/data/), and TPC-C using the [HammerDB instructions](../../cases/tpcc/data/). The table gives the observed evaluation counts; some generated counts can vary when preparing new instances.

## Procedure

1. Prepare the S, M and L source databases for each case and record their actual row counts.
2. Use the [experimental environment and measurement protocol](../../environment/) and follow the [migrator execution guide](../../software/database-migrator/RUNNING.md). For Music Streaming, select canonical migration **without adaptation**.
3. Run one complete migration for each of the six configurations, with an empty dedicated MongoDB target. Use batches of 1,000 and keep indexes enabled, as in the paper.
4. Retain the automatically generated migration report (`Migration_times.txt`), source row counts and source/target database sizes for each configuration. Preserve each report before starting the next run.
5. Calculate throughput as the total number of source rows processed divided by the data-migration elapsed time in seconds. Use the measured time before rounding where available.

The elapsed time covers source-data access, instance transformation, relationship resolution and document writes. It excludes schema transformations and program initialization; total Java process wall time is therefore not the same measurement. Embedded source rows count as processed rows even when they do not produce independent documents.

Each configuration in the paper was executed once. The results are descriptive measurements, not averages over repeated runs.

## Results

Tables 9 and 10 report source/target sizes, elapsed times and throughput. Their values are available as [Music Streaming CSV](../../results/scaling/music-streaming.csv) and [TPC-C CSV](../../results/scaling/tpcc.csv).

Compare the behavior across S, M and L as well as migration correctness. Absolute execution times depend on the hardware and runtime environment and need not match the paper exactly.

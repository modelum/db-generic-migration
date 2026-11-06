# Reported evaluation results

The CSV tables contain the results reported in the paper. Elapsed times are expressed in seconds. The `paper_locator` column, where present, identifies the corresponding table or section.

| Files | Content |
| --- | --- |
| [scaling/music-streaming.csv](scaling/music-streaming.csv) | Music Streaming S/M/L sizes, elapsed times and throughput, Table 9 |
| [scaling/tpcc.csv](scaling/tpcc.csv) | TPC-C S/M/L sizes, elapsed times and throughput, Table 10 |
| [flat-copy/summary.csv](flat-copy/summary.csv) | L-instance baseline and complete-migration times, Section 6.4 |
| [flat-copy/music-streaming-L-by-table.csv](flat-copy/music-streaming-L-by-table.csv) | Music Streaming L per-table row counts and baseline timings |
| [roundtrip/preservation-metrics.csv](roundtrip/preservation-metrics.csv) | Precision, recall and F1, Table 8 |
| [query-equivalence/summary.csv](query-equivalence/summary.csv) | Q1-Q9 cardinalities and equivalence indicators, Table 11 |

## Interpretation

**Scaling.** Source and target sizes are expressed in MB, as in the paper. Execution times depend on the hardware and runtime environment.

**Flat-copy.** The Music Streaming L per-table counts total 28,599,970 rows. The displayed per-table timings, rounded to seconds, sum to the reported 370-second baseline.

**Round-trip.** NA denotes categories reported as not applicable, rather than zero or a missing measurement. Key matching follows the structural and semantic criteria in the paper, rather than requiring identical column names or arity.

**Queries.** Cardinalities refer to S for Music Streaming and TPC-C and to the fixed Northwind instance. Equivalence indicators cover S and M for Music Streaming and TPC-C. Approximate cardinalities remain approximate. Q3's cardinality of 1 denotes one aggregate output row, not one qualifying user.

[Query-output examples](query-equivalence/examples/) contain partial examples for Q2/Q3 and Q5-Q9. To reproduce the comparison, check complete query results using the [query-equivalence instructions](../experiments/query-equivalence/README.md#reproduction). Retain new execution outputs separately from the reported results.

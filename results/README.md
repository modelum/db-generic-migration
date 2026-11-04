# Reported evaluation results

This directory makes the results reported in the paper available as CSV tables.

| Files | Content | Evidence type |
| --- | --- | --- |
| [scaling/music-streaming.csv](scaling/music-streaming.csv) | Music Streaming S/M/L sizes, elapsed times and throughput, Table 9 | Paper transcription |
| [scaling/tpcc.csv](scaling/tpcc.csv) | TPC-C S/M/L sizes, elapsed times and throughput, Table 10 | Paper transcription |
| [flat-copy/summary.csv](flat-copy/summary.csv) | Both L flat-copy baselines and complete-migration times, Section 6.4 | Paper transcription |
| [flat-copy/music-streaming-L-by-table.csv](flat-copy/music-streaming-L-by-table.csv) | Eight per-table counts and times from existing output | Recovered-output extraction |
| [roundtrip/preservation-metrics.csv](roundtrip/preservation-metrics.csv) | Precision, recall and F1 for three cases, Table 8 | Paper transcription |
| [query-equivalence/summary.csv](query-equivalence/summary.csv) | Q1-Q9 cardinalities and reported equivalence indicators, Table 11 | Paper transcription |

## Sources and interpretation

The CSV tables contain the values reported in the paper. The Music Streaming baseline breakdown contains per-table timings from the evaluation record. Elapsed times are expressed in seconds.

Source and target sizes retain the MB labels and values reported in the paper; no conversion to MiB or reinterpretation of allocation/index overhead has been applied. Published precision is preserved. Ratios and F1 values are not recalculated from already rounded reported figures.

The Music Streaming L per-table counts sum to 28,599,970 and displayed times sum to 370 seconds, matching the reported baseline. These are sums of individual table timings rounded to seconds, not evidence of a global timer across all processes. The extraction does not by itself verify the runtime version or every execution setting.

Round-trip NA values represent categories reported as not applicable (the dash in Table 8); they are not zero, one or missing measurements. Key matching follows the paper's structural/semantic criteria rather than requiring identical column names or arity.

For query results, approximate cardinalities remain explicitly approximate. The cardinality column refers to S for Music Streaming/TPC-C and the fixed Northwind instance; the equivalence indicator covers S and M for the scalable cases as described in the paper. Q3's value 1 denotes one aggregate output row, not necessarily one qualifying user.

No additional adaptation results or unreported measurements are introduced. Any future reproduction outputs should be stored separately and identified as new runs.

## Query evaluation records

The original query evaluation checked corresponding SQL and MongoDB results during execution. Evaluation documents preserve result counts and illustrative output; larger outputs were not always copied in full. Those partial records should not be treated as complete replayable exports. The TPC-C comparison document records agreement between the SQL and MongoDB results. See the [query experiment](../experiments/query-equivalence/) for scope and the new-run comparison procedure. The Table 11 CSV remains a transcription of the paper, not a new computation from partial examples.

## Query output examples

[query-equivalence/examples](query-equivalence/examples/) contains literal output excerpts from the evaluation records, with one text file per query and database side. Files retain display column order, whitespace, numeric formatting and date strings; they are not normalized exports. Ellipses in larger outputs remain as recorded. A zero-valued digit or an array is not silently converted into another representation.

| Queries | Stored examples per side | Source |
| --- | --- | --- |
| Q2 | Three style rows | Music Streaming SQL/MongoDB evaluation scripts |
| Q3 | Scalar count: 4 | Music Streaming SQL/MongoDB evaluation scripts |
| Q5 | Three order-detail rows | Northwind SQL/MongoDB evaluation scripts |
| Q6 | One manager row | Northwind SQL/MongoDB evaluation scripts |
| Q7 | Ten district rows | TPC-C comparison document |
| Q8 | Ten payment-history rows | TPC-C comparison document |
| Q9 | Twelve example rows; document reports 5,486 | TPC-C comparison document |

Q1 and Q4 do not have a paired example set in this folder. The examples do not cover every dataset size or prove full-result equality by themselves. They illustrate the evaluation results alongside the paper's Table 11 summary. Query timing annotations and additional exploratory queries are omitted. For a new comparison use [comparison instructions](../experiments/query-equivalence/README.md#reproduction), including the output-field correspondence and explicit treatment of dates and numeric values.

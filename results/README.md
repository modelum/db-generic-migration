# Reported evaluation results

This directory makes the results reported in the paper available as CSV tables. It does not contain new experimental runs or claim that the recovered code/model candidates have reproduced these values.

| Files | Content | Evidence type |
| --- | --- | --- |
| [scaling/music-streaming.csv](scaling/music-streaming.csv) | Music Streaming S/M/L sizes, elapsed times and throughput, Table 9 | Paper transcription |
| [scaling/tpcc.csv](scaling/tpcc.csv) | TPC-C S/M/L sizes, elapsed times and throughput, Table 10 | Paper transcription |
| [flat-copy/summary.csv](flat-copy/summary.csv) | Both L flat-copy baselines and complete-migration times, Section 6.4 | Paper transcription |
| [flat-copy/music-streaming-L-by-table.csv](flat-copy/music-streaming-L-by-table.csv) | Eight per-table counts and times from existing output | Recovered-output extraction |
| [roundtrip/preservation-metrics.csv](roundtrip/preservation-metrics.csv) | Precision, recall and F1 for three cases, Table 8 | Paper transcription |
| [query-equivalence/summary.csv](query-equivalence/summary.csv) | Q1-Q9 cardinalities and reported equivalence indicators, Table 11 | Paper transcription |

## Sources and interpretation

[Sources and versions](../SOURCES.md) identifies the manuscript used for the result transcriptions, the corresponding tables and the baseline source. The public [arXiv preprint](https://arxiv.org/abs/2604.22415) may differ from that manuscript snapshot; do not assume that every arXiv version has the same table numbering or results.

`paper_transcription` means a value copied from the paper, with elapsed times expressed in seconds. `recovered_output_extraction` means a numeric value extracted from an existing output document. Neither label means a new run, an independently verified result, or a saved EMF Compare report. Internal working documents and earlier measurements are not published here.

Source and target sizes retain the MB labels and values reported in the paper; no conversion to MiB or reinterpretation of allocation/index overhead has been applied. Published precision is preserved. Ratios and F1 values are not recalculated from already rounded reported figures.

The Music Streaming L per-table counts sum to 28,599,970 and displayed times sum to 370 seconds, matching the reported baseline. These are sums of individual table timings rounded to seconds, not evidence of a global timer across all processes. The extraction does not by itself verify the runtime version or every execution setting.

Round-trip NA values represent categories reported as not applicable (the dash in Table 8); they are not zero, one or missing measurements. Key matching follows the paper's structural/semantic criteria rather than requiring identical column names or arity.

For query results, approximate cardinalities remain explicitly approximate. The cardinality column refers to S for Music Streaming/TPC-C and the fixed Northwind instance; the equivalence indicator covers S and M for the scalable cases as described in the paper. The summary is not an assertion that the recovered candidate SQL/JavaScript files have been executed or validated against those results. Q3's value 1 denotes one aggregate output row, not necessarily one qualifying user.

No additional adaptation results or unreported measurements are introduced. Any future reproduction outputs should be stored separately and identified as new runs.

## Query evaluation records

The original query evaluation checked corresponding SQL and MongoDB results during execution. Evaluation documents preserve result counts and illustrative output; larger outputs were not always copied in full. Those partial records should not be treated as complete replayable exports. The TPC-C comparison document records numerical agreement after resolution of the earlier Decimal128 issue. See the [query experiment](../experiments/query-equivalence/) for scope and the new-run comparison procedure. The Table 11 CSV remains a transcription of the paper, not a new computation from partial examples.

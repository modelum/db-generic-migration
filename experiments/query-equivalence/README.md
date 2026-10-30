# Query-result equivalence

Recovered candidate pairs associated with Table 11 are available for [Music Streaming Q1-Q3](../../cases/music-streaming/queries/), [Northwind Q4-Q6](../../cases/northwind/queries/) and [TPC-C Q7-Q9](../../cases/tpcc/queries/). The queries originated as extractions from evaluation documents; subsequent repository revisions and original/current hashes are recorded in each case provenance file. They are not an executed or validated final suite.

The paper evaluates Music Streaming and TPC-C over S and M, and Northwind over its fixed instance. Reproduction requires the matching generated data representation, selected query parameters, scalar/date normalization, and comparison preserving duplicate tuples while disregarding physical order when not specified.

Execution instructions, normalized exports and verified comparison results are not yet included. The presence of SQL and JavaScript files does not establish equivalence or exact correspondence with the final published execution.

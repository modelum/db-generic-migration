# Query-result equivalence

The evaluation compared the results of corresponding PostgreSQL and MongoDB queries Q1-Q9: [Music Streaming Q1-Q3](../../cases/music-streaming/queries/), [Northwind Q4-Q6](../../cases/northwind/queries/) and [TPC-C Q7-Q9](../../cases/tpcc/queries/). Music Streaming and TPC-C were evaluated over S and M; Northwind uses its fixed instance. Table 11 reports equivalence for all query pairs.

## Evaluation records

SQL/JavaScript evaluation scripts and comparison documents record query text, result counts and examples of returned values. Full results were checked during execution; only subsets of some larger outputs were retained in the documents. The stored examples are therefore illustrative records, not complete machine-readable exports of every query/instance pair.

Column display order and date/numeric formatting are interpreted through the corresponding projected fields and data types. They do not by themselves establish a difference in the underlying results. The current Music Q1 addresses the canonical embedded-playlist structure; the independent-playlist variant belongs to the adaptation scenario.

The final TPC-C comparison document records matching SQL/MongoDB results with numeric values for Q7-Q9. The comparison records for Q7 and Q8 contain ten results per side; Q9 reports 5,486 per side with example rows. These records concern the documented instance, not an independently regenerated dataset.

The [stored output examples](../../results/query-equivalence/examples/) include Q2/Q3 and Q5-Q9; their scope and original display formats are described in [results](../../results/README.md#query-output-examples).

## Reproduction

1. Run each SQL query against the source database and its MongoDB counterpart against the migrated database, using the same logical filter parameters.
2. Compare corresponding output fields and values, accounting for equivalent numeric and date representations and differences in displayed column order.
3. Compare the complete results, preserving duplicate rows. Row order need not match unless ordering itself is being checked. For Q3, compare the returned count value.

Music Q1 uses embedded playlists. If data are regenerated, choose parameters present in that instance on both sides; original identifiers and exact cardinalities may differ. The [stored examples](../../results/query-equivalence/examples/) illustrate results and do not replace comparison of the complete outputs from a new run.

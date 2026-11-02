# Query-result equivalence

The evaluation compared the results of corresponding PostgreSQL and MongoDB queries Q1-Q9: [Music Streaming Q1-Q3](../../cases/music-streaming/queries/), [Northwind Q4-Q6](../../cases/northwind/queries/) and [TPC-C Q7-Q9](../../cases/tpcc/queries/). Music Streaming and TPC-C were evaluated over S and M; Northwind uses its fixed instance. Table 11 reports equivalence for all query pairs.

## Evaluation records

SQL/JavaScript evaluation scripts and comparison documents record query text, result counts and examples of returned values. Full results were checked during execution; only subsets of some larger outputs were retained in the documents. The stored examples are therefore illustrative records, not complete machine-readable exports of every query/instance pair.

Column display order and date/numeric formatting are interpreted through the corresponding projected fields and data types. They do not by themselves establish a difference in the underlying results. The current Music Q1 addresses the canonical embedded-playlist structure; the independent-playlist variant belongs to the adaptation scenario.

The final TPC-C comparison document records matching SQL/MongoDB results with numeric values for Q7-Q9. The comparison records for Q7 and Q8 contain ten results per side; Q9 reports 5,486 per side with example rows. These records concern the documented instance, not an independently regenerated dataset.

## Reproduction

[COMPARISON.md](COMPARISON.md) gives a procedure for a new run: matching parameters, projected fields, duplicate multiplicities and numeric/date interpretation. It is distinct from the original evaluation records. Query revisions are recorded in Git history; publication results are in [results/query-equivalence](../../results/query-equivalence/).

No additional query run was performed while preparing this package, and the illustrative records do not replace a complete export when performing a new automated comparison.

# Query-result equivalence

The evaluation compared the results of corresponding PostgreSQL and MongoDB queries Q1-Q9: [Music Streaming Q1-Q3](../../cases/music-streaming/queries/), [Northwind Q4-Q6](../../cases/northwind/queries/) and [TPC-C Q7-Q9](../../cases/tpcc/queries/). Music Streaming and TPC-C were evaluated over S and M; Northwind uses its fixed instance. Table 11 reports equivalence for all query pairs.

## Result examples

The [stored output examples](../../results/query-equivalence/examples/) cover Q2/Q3 and Q5-Q9. Some larger results were retained only as excerpts; these examples do not replace comparison of complete results from a new execution.

## Reproduction

1. Run each SQL query against the source database and its MongoDB counterpart against the migrated database, using the same logical filter parameters.
2. Compare corresponding output fields and values, accounting for equivalent numeric and date representations and differences in displayed column order.
3. Compare the complete results, preserving duplicate rows. Row order need not match unless ordering itself is being checked. For Q3, compare the returned count value.

The [stored examples](../../results/query-equivalence/examples/) illustrate results and do not replace comparison of the complete outputs from a new run.

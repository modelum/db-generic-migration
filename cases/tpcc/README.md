# tpcc

[source/](source/) contains the recovered source schema files, selection notes and checksums. See its README before using the SQL.

Status: source files copied and integrity checked; database loading, schema correspondence and end-to-end reproduction remain unverified. Data generation and final query execution remain unverified during packaging; reported results are available under [results](../../results/). An expected-model candidate has been recovered; independent review and comparison remain pending. Generated model candidates are documented below.

## Generated model candidates

[generated/](generated/) contains recovered U-Schema and Document models with selection notes. Their XML and selected structural patterns have been checked. Recovered expected artifacts are linked below; full semantic comparison and regeneration have not been rerun during packaging.

## Expected model recovered

[expected/](expected/) contains a specification-derived expected-model candidate and its original notes. It remains distinct from the recovered generated models. This reference has not yet been validated against a final generated output.

## Recovered query candidates

[queries/](queries/) contains SQL/MongoDB candidate pairs associated with Table 11, initially extracted from evaluation documents, with subsequent revisions recorded in their provenance files. Execution validation has not been performed during packaging.

## Data preparation

[data/](data/) documents the HammerDB 5.0 TPROC-C settings for S/M/L, observed Table 7 row counts and a read-only inventory query. It links the official release and build documentation. The preparation workflow has not been executed during packaging.

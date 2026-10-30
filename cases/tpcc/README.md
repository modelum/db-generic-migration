# tpcc

[source/](source/) contains the recovered source schema files, selection notes and checksums. See its README before using the SQL.

Status: source files copied and integrity checked; database loading, schema correspondence and end-to-end reproduction remain unverified. Data preparation, final query validation and results are pending. An expected-model candidate has been recovered; independent review and comparison remain pending. Generated model candidates are documented below.

## Generated model candidates

[generated/](generated/) contains recovered U-Schema and Document models with selection notes. Their XML and selected structural patterns have been checked. Independent expected models, full semantic comparison, and regeneration remain pending.

## Expected model recovered

[expected/](expected/) contains a specification-derived expected-model candidate and its original notes. It remains distinct from the recovered generated models. This reference has not yet been validated against a final generated output.

## Recovered query candidates

[queries/](queries/) contains SQL/MongoDB candidate pairs associated with Table 11, extracted without semantic changes. This updates the earlier query availability note: final selection and execution validation remain pending.

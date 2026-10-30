# Northwind

- [source/](source/): recovered relational XMI and original DDL, with selection and provenance notes.
- [data/](data/): recovered fixed dataset, PostgreSQL loading procedure, and count checks.

Status: source and dataset files copied with integrity checks; loading aids inspected statically but not executed. Upstream attribution, expected-model validation, validation of queries Q4-Q6 and end-to-end migration validation remain pending.

## Generated model candidates

[generated/](generated/) contains recovered U-Schema and Document models with selection notes. Their XML and selected structural patterns have been checked. Recovered expected artifacts are linked below; full semantic comparison and regeneration have not been rerun during packaging.

## Recovered query candidates

[queries/](queries/) contains SQL/MongoDB candidate pairs associated with Table 11, initially extracted from evaluation documents, with subsequent revisions recorded in their provenance files. Execution validation has not been performed during packaging.

## Recovered expected models

[expected/](expected/) contains recovered Document and U-Schema expected models, preserved unchanged with provenance and hashes. This updates earlier availability notes: model conformance, independent review and comparison remain unverified.

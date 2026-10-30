# Northwind

- [source/](source/): recovered relational XMI and original DDL, with selection and provenance notes.
- [data/](data/): recovered fixed dataset, PostgreSQL loading procedure, and count checks.

Status: source and dataset files copied with integrity checks; loading aids inspected statically but not executed. Upstream attribution, expected-model validation, validation of queries Q4-Q6 and end-to-end migration validation remain pending.

## Generated model candidates

[generated/](generated/) contains recovered U-Schema and Document models with selection notes. Their XML and selected structural patterns have been checked. Independent expected models, full semantic comparison, and regeneration remain pending.

## Recovered query candidates

[queries/](queries/) contains SQL/MongoDB candidate pairs associated with Table 11, extracted without semantic changes. This updates the earlier query availability note: final selection and execution validation remain pending.

## Recovered expected models

[expected/](expected/) contains recovered Document and U-Schema expected models, preserved unchanged with provenance and hashes. This updates earlier availability notes: model conformance, independent review and comparison remain unverified.

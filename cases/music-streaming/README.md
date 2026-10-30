# music-streaming

[source/](source/) contains the recovered source schema files, selection notes and checksums. See its README before using the SQL.

Status: source files copied and integrity checked; database loading, schema correspondence and end-to-end reproduction remain unverified. Data-generation execution, expected-model validation, final query validation and results are pending. Generated model candidates are documented below.

## Generated model candidates

[generated/](generated/) contains recovered U-Schema and Document models with selection notes. Their XML and selected structural patterns have been checked. Independent expected models, full semantic comparison, and regeneration remain pending.

## Recovered query candidates

[queries/](queries/) contains SQL/MongoDB candidate pairs associated with Table 11, extracted without semantic changes. This updates the earlier query availability note: final selection and execution validation remain pending.

## Recovered expected models

[expected/](expected/) contains recovered Document and U-Schema expected models, preserved unchanged with provenance and hashes. This updates earlier availability notes: model conformance, independent review and comparison remain unverified.

## Data generator

[data/](data/) contains the unchanged source generator, Table 6 parameters and a prepared copy with only DATASET_CONFIG replaced. The AWS configuration is reconstructed from the paper; execution and exact dataset reproduction have not been verified.

# music-streaming

[source/](source/) contains the recovered source schema files, selection notes and provenance. See its README before using the SQL.

Status: source files copied and integrity checked; database loading, schema correspondence and end-to-end reproduction remain unverified. Data-generation execution, expected-model validation and final query execution remain unverified during packaging; reported results are available under [results](../../results/). Generated model candidates are documented below.

## Generated model candidates

[generated/](generated/) contains recovered U-Schema and Document models with selection notes. Their XML and selected structural patterns have been checked. Recovered expected artifacts are linked below; full semantic comparison and regeneration have not been rerun during packaging.

## Recovered query candidates

[queries/](queries/) contains SQL/MongoDB candidate pairs associated with Table 11, initially extracted from evaluation documents, with subsequent revisions recorded in their provenance files. Execution validation has not been performed during packaging.

## Recovered expected models

[expected/](expected/) contains recovered Document and U-Schema expected models, preserved unchanged with provenance and hashes. This updates earlier availability notes: model conformance, independent review and comparison remain unverified.

## Data generator

[data/](data/) contains the unchanged source generator, Table 6 parameters and a prepared copy with only DATASET_CONFIG replaced. The AWS configuration is reconstructed from the paper; execution and exact dataset reproduction have not been verified.

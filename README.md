# db-generic-migration

Replication package under preparation for *A Model-Driven Approach to Database Migration with a Unified Data Model*.

The package is organized around the final empirical evaluation in the paper: Music Streaming, TPC-C, Northwind, schema validation, round-trip reconstruction, trace-aware adaptation, data migration scaling, a flat-copy baseline, and query-result equivalence.

## Start here

This is an initial artifact recovery snapshot, not a validated runnable release.

- [Pending questions and completion criteria](docs/PENDIENTES.md) (Spanish).
- [Word report](docs/Estado_y_pendientes_replication_package.docx) (Spanish).
- [Reproduction scope](REPRODUCING.md).

## Current status

The folder structure and introductory documentation are prepared. Recovered source schema files for the three cases have been copied with provenance notes and checksums. Their database loading and end-to-end reproducibility have not been verified. The Northwind fixed dataset and statically checked loading aids are also included; database execution remains pending. Recovered generated model candidates for the three cases are included with explicit selection limitations; independent expected-model validation and regeneration remain pending. A specification-derived TPC-C expected-model candidate and comparison inventory are included; discrepancies remain unresolved. Other experimental artifacts are pending. The development repository remains separate.

## Contents

- [software](software/): metamodels, schema transformations, data migrator, trace updater, and flat-copy implementation.
- [environment](environment/): dependencies and execution environment.
- [cases](cases/): source schemas, data preparation, expected models, generated reference models, and queries for the three cases.
- [experiments](experiments/): procedures corresponding to the experiments in the paper.
- [results](results/): verified reference evidence for the published evaluation.
- [REPRODUCING.md](REPRODUCING.md): reproduction scope and preparation status.

## Artifact conventions

Independently specified expected models must remain separate from generated models. Canonical and adapted schemas must be clearly identified. Original reference results must remain separate from new execution outputs. Historical plans and preliminary experiments do not define the scope of this package.

License, citation metadata, exact software versions, and runnable instructions will be added after verification. No license is granted by this placeholder documentation.

## Open metamodel checks

The current [metamodel candidates](software/metamodels/) come from the recovered OneDrive archive. They include targetShareability and allow optional ownership and key references as described in their README. Compatibility with the already recovered model candidates and executable libraries is still pending, especially datatype defaults and required Array names. Earlier development metamodels are retained as historical snapshots.

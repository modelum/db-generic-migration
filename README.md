# db-generic-migration

Replication package under preparation for *A Model-Driven Approach to Database Migration with a Unified Data Model*.

## Paper

María J. Ortín, José R. Hoyos, and Jesus García-Molina. **A Model-Driven Approach to Database Migration with a Unified Data Model**. arXiv:2604.22415, 2026.

- [Read the preprint on arXiv](https://arxiv.org/abs/2604.22415)
- [DOI: 10.48550/arXiv.2604.22415](https://doi.org/10.48550/arXiv.2604.22415)

The arXiv link provides the public preprint. The artifact collection is being organized around the evaluation cases and experiments, with the status of individual artifacts documented in their folders.

## Package status

This repository is under preparation and is not yet a validated runnable release. It currently includes source schemas for Music Streaming, TPC-C and Northwind, the fixed Northwind dataset and loading aids, recovered model candidates, metamodel definitions, and eight implementation source projects with provenance information. End-to-end reproduction has not yet been verified.

## Contents

- [cases](cases/): source schemas, data preparation and model artifacts for the three cases.
- [software](software/): metamodels, implementation source snapshots and dependency documentation.
- [environment](environment/): execution environment documentation.
- [experiments](experiments/): schema validation, round-trip reconstruction, trace-aware adaptation, scaling, flat-copy baseline and query equivalence.
- [results](results/): folder reserved for verified reference results.
- [REPRODUCING.md](REPRODUCING.md): reproduction scope and current availability.

Artifact notes distinguish recovered candidates from validated references and original files from derived loading aids. Expected models are kept separate from generated outputs; new execution outputs should remain separate from published reference results.

## Citation

```bibtex
@misc{ortin2026migration,
  title         = {A Model-Driven Approach to Database Migration with a Unified Data Model},
  author        = {Ortín, María J. and Hoyos, José R. and García-Molina, Jesus},
  year          = {2026},
  eprint        = {2604.22415},
  archivePrefix = {arXiv},
  primaryClass  = {cs.DB},
  doi           = {10.48550/arXiv.2604.22415},
  url           = {https://arxiv.org/abs/2604.22415}
}
```

License and complete software citation metadata will be supplied with the finalized package. No license is granted by this placeholder documentation.

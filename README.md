# db-generic-migration

Replication materials for *A Model-Driven Approach to Database Migration with a Unified Data Model*.

## Paper

María J. Ortín, José R. Hoyos, and Jesus García-Molina. **A Model-Driven Approach to Database Migration with a Unified Data Model**. arXiv:2604.22415, 2026.

- [Read the preprint on arXiv](https://arxiv.org/abs/2604.22415)
- [DOI: 10.48550/arXiv.2604.22415](https://doi.org/10.48550/arXiv.2604.22415)

The arXiv link provides the public preprint. The artifact collection is being organized around the evaluation cases and experiments, with the status of individual artifacts documented in their folders.

## Package status

This repository provides the implementation sources, models, data preparation materials, queries, and reported results supporting the paper. Artifact availability and reproduction instructions are documented in the corresponding folders. End-to-end reproduction from a clean checkout has not yet been verified.

The package includes the three case studies, recovered expected and generated models, round-trip artifacts, Music Service adaptation outputs, and Q1-Q9 query pairs. Original artifacts, repository revisions, derived preparation aids, and paper-transcribed results are identified through their accompanying provenance documentation.

## Contents

- [cases](cases/): source schemas, data preparation and model artifacts for the three cases.
- [software](software/): metamodels, implementation source snapshots and dependency documentation.
- [environment](environment/): execution environment documentation.
- [experiments](experiments/): schema validation, round-trip reconstruction, trace-aware adaptation, scaling, flat-copy baseline and query equivalence.
- [results](results/): published results transcribed as CSV, plus a recovered baseline timing breakdown with provenance.
- [REPRODUCING.md](REPRODUCING.md): reproduction scope and current availability.

Artifact notes distinguish recovered candidates from validated references and original files from derived loading aids. Expected models are kept separate from generated outputs; new execution outputs should remain separate from published reference results.

## Funding

This work was supported by project PID2020-117391GB-I00 (ASE4AIIDB), funded by MICIU/AEI/10.13039/501100011033 (Spain), and co-funded by ERDF/EU.

## Citation

Download the paper reference as [BibTeX](CITATION.bib).

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

A reuse license has not yet been specified. The paper reference above identifies the publication supported by these materials.

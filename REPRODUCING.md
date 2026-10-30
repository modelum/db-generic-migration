# Reproducing the evaluation

This document is a preparation checklist, not yet an executable reproduction guide. Follow the final paper when selecting artifacts and experimental configurations.

| Experiment | Scope | Status |
| --- | --- | --- |
| Incremental mapping tests and schema validation | Four transformations; generated versus expected Document models for three cases | Artifacts and instructions pending |
| Round-trip reconstruction | Music Streaming, TPC-C, Northwind; Table 8 | Artifacts and instructions pending |
| Trace-aware adaptation | Separate supported operations on TPC-C S | Artifacts and instructions pending |
| Data migration scaling | Music Streaming and TPC-C S/M/L; Tables 9 and 10 | Artifacts and instructions pending |
| Flat-copy baseline | Music Streaming L and TPC-C L; batch 1,000 | Artifacts and instructions pending |
| Query-result equivalence | Q1-Q9; S/M for scalable cases and fixed Northwind; Table 11 | Artifacts and instructions pending |

For each experiment, the completed guide must specify prerequisites, exact input versions, preparation commands, execution commands, expected outputs, validation, and the corresponding paper section or table. Report hardware-dependent timings as observations, not exact pass/fail targets.

Large datasets may be supplied through documented generation or external downloads with provenance and checksums. Distinguish regeneration of comparable datasets from recovery of the exact original instances.

Before publication, verify the software and executable versions, dataset preparation, independent expected models, query versions and normalization, reference results, license, and citation metadata.

# Round-trip schema preservation

The experiment applies Relational -> U-Schema -> Document -> U-Schema -> Relational to Music Streaming, Northwind and TPC-C. Compare the reconstructed relational model with the original relational input to assess the information preserved through the chain.

## Models and results

`recovered-models/<case>/forward/` supplies Music Streaming and Northwind input/intermediate/Document models. `reverse/` supplies reconstructed U-Schema and relational models for all three cases. The TPC-C forward models are under [source](../../cases/tpcc/source/) and [generated](../../cases/tpcc/generated/). The folder name is retained for existing links; source versions are summarized in `SOURCES.md` at the repository root.

The evaluation used Eclipse EMF Compare and visual inspection. Table 8 precision, recall and F1 values are supplied in [results/roundtrip](../../results/roundtrip/). No saved EMF Compare report is included. Forward expected-model validation is a separate experiment.

Music forward outputs in this experiment differ in attribute types, synthetic key names and aggregate back-references from the canonical case generated models; preserve the selected comparison pairs rather than mixing variants. Northwind forward models match the case generated pair. Later Ecore revisions are not required for the reported experiment; use matching implementation/runtime versions.

## Comparing the round-trip models

This schema-only inspection does not require PostgreSQL or MongoDB. Use Eclipse with EMF and EMF Compare, with the matching Relational metamodel registered. Review the package's metamodel compatibility note before interpreting load/validation failures as model differences.

| Case | Original relational reference | Reconstructed relational model |
| --- | --- | --- |
| Music Streaming | [original](recovered-models/music-streaming/forward/MusicService-relational.xmi) | [reconstructed](recovered-models/music-streaming/reverse/MusicServiceDoc-relational-generated.xmi) |
| Northwind | [original](recovered-models/northwind/forward/Northwind-relational.xmi) | [reconstructed](recovered-models/northwind/reverse/NorthwindDoc-relational-generated.xmi) |
| TPC-C | [original](../../cases/tpcc/source/TPC-C-relational-sinPK.xmi) | [reconstructed](recovered-models/tpcc/reverse/TPC-CDoc-relational-generated.xmi) |

1. Import copies of a pair into an Eclipse workspace and check that both load using the intended Relational metamodel.
2. Select the two relational models and invoke the installed EMF Compare comparison action. Record which is original and which is reconstructed, plus EMF/EMF Compare versions and matching options. The historical comparison configuration was not saved.
3. Inspect table, column, key/foreign-key, constraint and datatype correspondences. Compare schema semantics as described in the paper; generated names or composite-identifier representation can differ. The original and reconstructed models are not expected to be textually identical.
4. Consult [preservation-metrics.csv](../../results/roundtrip/preservation-metrics.csv) for the reported Table 8 values. EMF difference counts alone are not precision/recall counts: retain the semantic matching decisions used to classify true positives, false positives and false negatives. Non-applicable categories remain NA.
5. Save the comparison and any calculated counts as a new inspection record, separate from the supplied models and paper-transcribed CSV.

Forward expected-model comparisons belong to [schema validation](../schema-validation/); expected Document/U-Schema files are not substituted for the original relational reference in the table above. This procedure documents inspection of supplied artifacts, not an already validated rerun.

## Regeneration entry point

The preserved [RoundTripV2 launcher](../../software/source-projects/RoundTripV2/src/um/modelum/migration/main/Principal.java) uses relative `roundtrip/direct/` and `roundtrip/reverse/` directories and hard-coded case filenames rather than database arguments. It requires the EMF/Xtend and metamodel/Trace dependencies listed in [DEPENDENCIES.md](../../software/source-projects/DEPENDENCIES.md).

As preserved, `main` performs the Music relational-to-U-Schema step and a trace save/load test, then calls `System.exit(0)` before the remaining forward and reverse stages. Consequently no command for a complete round-trip is advertised as working in this snapshot. Reviewing that launcher and resolving the documented runtime compatibility are required before a supported regeneration command can be supplied. No launcher change is included here. Supplied outputs are not proof that this current entry point executes the complete chain.

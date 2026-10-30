# Reproducing the evaluation

## Start here

1. Prepare the source data using the instructions for [Music Streaming](cases/music-streaming/data/), [TPC-C](cases/tpcc/data/) or [Northwind](cases/northwind/data/).
2. Use the supplied Java 21 [configurable migrator](software/data-migrator/configurable-2026-10-30/) and follow the [migration guide](experiments/scaling/RUNNING.md). The developer has tested the supplied executable; the complete package has not been independently rerun from a clean checkout during preparation.
3. Select canonical migration or the predefined Music adaptation by the input basename, as explained below. Use a dedicated working directory and target database.
4. Compare schema artifacts through [schema validation](experiments/schema-validation/) and [round-trip](experiments/roundtrip/). For data-level checks use [Q1-Q9](experiments/query-equivalence/).
5. Consult [results](results/) for the paper's observations and retain any new outputs separately.

## Experiment map

| Experiment | Materials and instructions |
| --- | --- |
| Schema validation | [Generated/expected models and comparison](experiments/schema-validation/) |
| Round-trip | [Relational input/output pairs and EMF Compare procedure](experiments/roundtrip/) |
| Trace-aware adaptation | [Music Service operations, models and trace](experiments/trace-adaptation/) |
| Migration scaling | [S/M/L preparation and execution](experiments/scaling/) |
| Flat-copy | [Single-table baseline and full-case sequence](experiments/flat-copy/) |
| Query equivalence | [Q1-Q9 and proposed new-run comparison procedure](experiments/query-equivalence/) |

## Versions and interpretation

Use the runtime/metamodel versions associated with the evaluation implementation. The later Ecore revisions described in [metamodel notes](software/metamodels/) are subsequent development, not a prerequisite for reproducing the reported executions. The configurable JAR adds connection parameters; its presence does not retrospectively identify the executable used for every historical timing.

Canonical Music uses embedded playlists. A working copy renamed to `MusicService-canonical.xmi`, invoked with basename `MusicService-canonical`, avoids the predefined adaptation trigger. Basename `MusicService-relational` activates the sequential adaptation scenario. Original package models remain unchanged.

Music and TPC-C regeneration produces comparable instances, not necessarily identical UUIDs, timestamps or every observed row count. Query parameters must refer to the actual source/target pair. Timing observations depend on hardware and are not exact pass/fail targets.

The paper's adaptation case attribution is clarified in [trace adaptation](experiments/trace-adaptation/). License selection and a complete clean-checkout build/verification remain release tasks; the current materials support inspection of the paper and documented reproduction preparation.

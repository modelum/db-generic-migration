# Reproducing the evaluation

## Choose an experiment

- **Data migration:** prepare a source database for [Music Streaming](cases/music-streaming/data/), [TPC-C](cases/tpcc/data/) or [Northwind](cases/northwind/data/), then follow the [migration execution guide](software/database-migrator/RUNNING.md). Use canonical migration without adaptation for scaling and query equivalence.
- **Round-trip:** follow the [round-trip instructions](experiments/roundtrip/) for any of the three cases. This schema experiment does not require populated databases.
- **Schema adaptation:** follow the separate [Music Streaming adaptation experiment](experiments/trace-adaptation/).

For Music Streaming, the input model basename selects whether adaptation runs. Follow the model-selection instructions in the migration guide.

## Experiment map

| Experiment | Materials and instructions |
| --- | --- |
| Schema validation | [Generated and expected models, EMF Compare procedure](experiments/schema-validation/) |
| Round-trip | [Original and reconstructed schemas, execution and comparison](experiments/roundtrip/) |
| Trace-aware adaptation | [Music Streaming operations, models and trace](experiments/trace-adaptation/) |
| Migration scaling | [S/M/L execution and measurements](experiments/scaling/) |
| Flat-copy | [Single-table baseline and full-case sequence](experiments/flat-copy/) |
| Query equivalence | [SQL/MongoDB queries and result comparison](experiments/query-equivalence/) |

## Versions and interpretation

Use Java 21 and the [documented environment](environment/). The [metamodel directory](software/metamodels/) contains the definitions matching the implementation; `final-version/` contains subsequent revisions described in `COMPATIBILITY.txt`.

Regenerating Music Streaming and TPC-C can produce different identifiers, timestamps and some row counts. Use query parameters that refer to the actual source/target pair. Execution times depend on the environment and need not match the paper exactly.

Consult [results](results/) for the reported observations and retain new execution outputs separately. The paper's adaptation case-study attribution is clarified in [trace adaptation](experiments/trace-adaptation/).

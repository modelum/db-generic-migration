# Round-trip schema preservation

## Objective

Evaluate how much relational schema information is preserved through the transformation chain:

```text
Relational -> U-Schema -> Document -> U-Schema -> Relational
```

For Music Streaming, TPC-C and Northwind, compare the reconstructed relational model with the original relational input. This experiment concerns schemas and does not require PostgreSQL, MongoDB or populated datasets.

## Models and results

The following pairs are supplied for inspection:

| Case | Original relational model | Reconstructed relational model |
| --- | --- | --- |
| Music Streaming | [MusicService-relational.xmi](models/music-streaming/forward/MusicService-relational.xmi) | [MusicServiceDoc-relational-generated.xmi](models/music-streaming/reverse/MusicServiceDoc-relational-generated.xmi) |
| TPC-C | [TPC-C-relational-sinPK.xmi](models/tpcc/forward/TPC-C-relational-sinPK.xmi) | [TPC-CDoc-relational-generated.xmi](models/tpcc/reverse/TPC-CDoc-relational-generated.xmi) |
| Northwind | [Northwind-relational.xmi](models/northwind/forward/Northwind-relational.xmi) | [NorthwindDoc-relational-generated.xmi](models/northwind/reverse/NorthwindDoc-relational-generated.xmi) |

For each case, [models](models/) contains `forward/` (original relational model and generated U-Schema/Document models) and `reverse/` (reconstructed U-Schema and relational models). The TPC-C forward files are identical to the corresponding case artifacts. Precision, recall and F1 values reported in Table 8 are available in [preservation-metrics.csv](../../results/roundtrip/preservation-metrics.csv).

## Execution

Use the [RoundTripV2 project](../../software/source-projects/RoundTripV2/) with the dependencies listed in [DEPENDENCIES.md](../../software/source-projects/DEPENDENCIES.md).

1. Import and build the project in Eclipse with its matching metamodel runtime libraries and EMF/Xtend dependencies.
2. Set the working directory to the RoundTripV2 project directory. It must contain `roundtrip/direct/` and `roundtrip/reverse/`.
3. Place copies of the three original relational models in `roundtrip/direct/`, using the filenames shown in the table above. Keep reference models separately because execution writes generated files into these directories.
4. Launch `um.modelum.migration.main.Principal` as a Java application. The launcher processes all three cases and does not require command-line arguments.

The forward transformations write U-Schema and Document models in `roundtrip/direct/`. The reverse transformations read those Document models and write reconstructed U-Schema and relational models in `roundtrip/reverse/`.

## Comparison

Use Eclipse EMF Compare and visual inspection, as in the evaluation. Load the original and reconstructed relational models with their matching Relational metamodel and compare the corresponding tables, columns, primary/foreign keys, constraints and data types.

The comparison concerns schema information rather than textual identity: generated names and representations of composite identifiers can differ. If calculating precision, recall and F1, use the matching criteria described in the paper; the number of EMF differences is not itself one of these metrics.

This comparison uses the **original relational model** as the reference. Comparing forward-generated Document or U-Schema models with expected models is covered separately by [schema validation](../schema-validation/).

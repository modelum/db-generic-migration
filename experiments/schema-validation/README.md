# Schema validation

## Objective

Check that the generated target schemas represent the structures expected from the mapping rules. The evaluation used Eclipse EMF Compare and visual inspection for Music Streaming, TPC-C and Northwind.

## Models

| Case | Generated models | Expected models |
| --- | --- | --- |
| Music Streaming | [U-Schema and Document](../../cases/music-streaming/generated/) | [U-Schema and Document](../../cases/music-streaming/expected/) |
| TPC-C | [U-Schema and Document](../../cases/tpcc/generated/) | [Document](../../cases/tpcc/expected/) |
| Northwind | [U-Schema and Document](../../cases/northwind/generated/) | [U-Schema and Document](../../cases/northwind/expected/) |

Relational inputs are in each case's `source/` folder. The expected models describe intermediate U-Schema or target Document schemas; they are not references for reconstructed relational schemas.

## Comparison

1. Load the generated and expected models using the corresponding metamodel runtime in Eclipse.
2. Compare each pair of the same model type with EMF Compare.
3. Inspect the differences against the mapping rules, including entities/documents, attributes, identifiers, references and embedded structures. Representation differences should be interpreted rather than treated as textual mismatches alone.

For TPC-C, the evaluated mapping excludes the order_line-to-stock foreign key because it targets an associative table. The expected-model notes in its folder explain its scope and representation details.

The separate [round-trip experiment](../roundtrip/) compares reconstructed relational schemas with their original relational inputs.

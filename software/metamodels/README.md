# Metamodel definitions and versions

The Ecore files in this directory include revisions identified after the TPC-C experiments. They are supplied as model-definition material; the corresponding implementation update is pending. They must not be substituted silently for the Ecores/runtime libraries used in the reported evaluation.

## Later definition changes

| Metamodel | Later definition change | Relevance |
| --- | --- | --- |
| Document | Reference.targetShareability is present, Boolean, required, with default true | Supplies the property discussed in the final paper |
| Document | DataType includes STRING, INTEGER, DOUBLE, DECIMAL, BOOLEAN, DATE, TIME, DATETIME | Broader type support than the development Ecore |
| Document | Embedded.isMany and PrimitiveType.datatype are required; Array.name is added and required | Existing serialized models must be revalidated |
| Relational | Table.keys has lower bound zero | Allows tables such as TPC-C history without a primary key |
| U-Schema | SchemaType.aggregate has lower bound zero | Allows root entities without an owning aggregate |
| U-Schema | Aggregate.specifiedBy has unique=false; package name/prefix become USchema | The namespace URI remains unchanged; runtime alignment is pending |


## Reproducing the evaluation

Use the Ecore/runtime combination associated with the selected implementation and experimental models. The project snapshots retain their own definitions; [historical/development-snapshot](historical/development-snapshot/) also identifies the earlier definitions. Register only one variant of a namespace URI at a time.

The later changes do not invalidate the fact that the reported experiments executed with the earlier implementation. Adopting these revisions would require coordinated code/runtime updates and new validation; that is outside the current reproduction of those executions.

Datatype defaults and required features differ between versions. Loading an existing XMI under a different definition can change its interpretation even if XML parsing succeeds. See [COMPATIBILITY.txt](COMPATIBILITY.txt) for the principal implementation differences and `provenance.json` for version origins.

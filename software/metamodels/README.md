# Metamodel candidates recovered from OneDrive

The three Ecore files in this directory were copied byte-for-byte from `metamodels/ecore/` in the group OneDrive `metamodels.zip` archive received on 2026-10-30. Their archive provenance and SHA-256 hashes are recorded in `provenance.json` and `checksums.sha256`. They are the current recovered candidates; date or folder location alone does not establish the exact version used in the published executions.

## Changes relative to the development snapshot

| Metamodel | Recovered change | Relevance |
| --- | --- | --- |
| Document | Reference.targetShareability is present, Boolean, required, with default true | Supplies the property discussed in the final paper |
| Document | DataType includes STRING, INTEGER, DOUBLE, DECIMAL, BOOLEAN, DATE, TIME, DATETIME | Broader type support than the development Ecore |
| Document | Embedded.isMany and PrimitiveType.datatype are required; Array.name is added and required | Existing serialized models must be revalidated |
| Relational | Table.keys has lower bound zero | Allows tables such as TPC-C history without a primary key |
| U-Schema | SchemaType.aggregate has lower bound zero | Allows root entities without an owning aggregate |
| U-Schema | Aggregate.specifiedBy has unique=false; package name/prefix become USchema | The namespace URI remains unchanged; runtime alignment is pending |

These changes resolve the specific missing-feature and lower-bound discrepancies identified in the earlier Ecore files. They do not automatically validate the selected models or existing runtime libraries.

## Implementation alignment

The OneDrive Ecores are retained for the planned implementation update. See [COMPATIBILITY.txt](COMPATIBILITY.txt) for the three confirmed alignment areas and the distinction between deployment configuration and model independence.

## Compatibility checks still needed

The Document datatype enumeration has changed its default literal: BOOLEAN was zero in the development file, whereas STRING is zero here. Old XMI files with an omitted datatype can consequently acquire a different meaning when loaded with the new Ecore. Existing Array instances may also lack the newly required name. Do not silently relabel or migrate those files; validate them against the final mappings and software first.

For targetShareability, an omitted value now means true according to this Ecore. Its presence in the definition does not establish that existing generated models preserve every required false value. Check the mappings, generated models and expected models together.

Namespaces and XML parsing have been inspected, and copied bytes verified. Full EMF validation, alignment with generated Java packages/JARs, and regeneration of the case models have not yet been performed. No runtime libraries have been rebuilt or executed.

## Historical files

[historical/development-snapshot/](historical/development-snapshot/) preserves the three previous development Ecore candidates unchanged, with their original provenance. The earlier U-Schema variant without the aggregate backlink is also retained under historical/. Do not register multiple variants sharing the same namespace URI simultaneously.

# tpcc generated models

## Status

Recovered generated candidates for the canonical schema chain. These are not independently specified expected models, not outputs of a new run, and not yet validated final references. Original filenames and bytes are preserved.

## Selection and limitations

These models match the recovered AWS TPC-C sinPK U-Schema and Document models after line-ending normalization. The Document model contains seven independent document types, including history; order_line and new_order are embedded within orders. district and customer remain independent. These inspected patterns agree with Section 6.3.2. Complete expected-model comparison and regeneration with the selected software version remain pending.

## Provenance

Copied from `db-generic-migration-dev/RoundTripV2/roundtrip/direct`. The cross-case inventory is [generated-provenance.json](../../generated-provenance.json). The selected relational source is in the sibling `source/` directory. No transformation was executed during packaging.

## Metamodel alignment pending

The newer OneDrive Document Ecore changes datatype defaults and requires Array.name. These recovered generated models predate confirmation of that metamodel version; do not assume conformance or unchanged interpretation. Validate or regenerate them with the matching final software before promoting them to final references. See [metamodel notes](../../../software/metamodels/README.md).

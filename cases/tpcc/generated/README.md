# TPC-C generated schemas

This folder contains the U-Schema and Document outputs for the relational-to-document schema chain. The relational input is in [source](../source/) and comparison models are in [expected](../expected/).

The Document schema contains seven independent document types. `order_line` and `new_order` are embedded within `orders`; `district`, `customer` and `history` remain independent.

File versions are identified in [generated-provenance.json](../../generated-provenance.json). These files are preserved experiment artifacts; they were not regenerated during package preparation. See [metamodel version notes](../../../software/metamodels/) before loading them with later Ecore definitions.

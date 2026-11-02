# Case-study model notes

Each case separates relational inputs (`source`), transformation outputs (`generated`), expected models (`expected`), data preparation (`data`) and queries (`queries`). The notes below explain distinctions needed to use these artifacts; preparation and query procedures remain in their respective folders.

## Music Streaming

The relational input contains eight tables and matches the MusicService input in RoundTripV2. The original SQL includes `CREATE DATABASE musicdb-M` and `USE DATABASE musicdb-M`, which are not a ready-to-run PostgreSQL setup. Prepare the dedicated schema before using the [data generator](music-streaming/data/); do not execute the original SQL as a complete loading script. SQL/XMI datatype and constraint correspondence still requires review for a new loading procedure.

The canonical generated Document model embeds `playlist` and `most_recent_song` in `app_user`, with `playlist_song` inside `playlist`, and retains `is_premium`. The [adapted scenario](../experiments/trace-adaptation/) instead produces independent playlists and `user_data` without `is_premium`; it is kept separate.

Expected Document and U-Schema models support forward-schema comparison. They are not reconstructed relational reference models.

## Northwind

The relational XMI has 14 tables, 92 columns, 14 keys and 13 foreign keys. Its generated Document schema has 14 independent document types with no embedded properties. Expected Document and U-Schema models are supplied.

Use the [PostgreSQL 16 data-loading procedure](northwind/data/), which handles the obsolete `SET default_with_oids` setting while preserving the original DDL. The precise upstream revision of the supplied Northwind SQL has not been established.

## TPC-C

`TPC-C-relational-sinPK.xmi` has nine tables, 92 columns, eight keys and nine foreign keys. `history` has no key; the filename does not mean all tables lack primary keys. The SQL dump has eight primary keys and no declared foreign keys; it includes functions, indexes and ownership statements and identifies PostgreSQL/pg_dump 16.14. Logical foreign keys were added to the model after DDL extraction. See [HammerDB preparation](tpcc/data/) for dataset generation and model scope; these files are not a transactional workload result.

The generated Document schema has seven independent types. `order_line` and `new_order` are embedded in `orders`; `district`, `customer` and `history` remain independent.

The expected Document model and its derivation notes exclude order_line-to-stock because the evaluated mapping does not support foreign keys targeting associative tables. The expected model contains DECIMAL/temporal types and explicit false targetShareability values on reversed warehouse.districts, district.customers and customer.orders references. Those features differ from generated implementation artifacts; use the [metamodel version notes](../software/metamodels/COMPATIBILITY.txt) when interpreting differences, rather than editing the expected model to force agreement.

## Schema comparisons and versions

Forward validation compares generated schemas with expected schemas. [Round-trip validation](../experiments/roundtrip/) instead compares each reconstructed relational schema with its original relational input. The evaluation used EMF Compare and visual inspection; no new comparison is claimed during package preparation.

Original files remain unchanged. Their origins and versions are recorded in accompanying provenance metadata, including [generated-provenance.json](generated-provenance.json). Later Ecore revisions must not be silently imposed on the earlier experimental models. See [schema-validation instructions](../experiments/schema-validation/) for comparison context.

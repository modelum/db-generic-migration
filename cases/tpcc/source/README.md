# tpcc source schemas

The selected XMI matches RoundTripV2/roundtrip/direct/TPC-C-relational-sinPK.xmi in the development repository, ignoring line endings. It has nine tables, 92 columns, eight key elements and nine foreign-key elements. The history table has no key. The filename sinPK is retained from the original; it does not mean that all tables lack primary keys.

The PostgreSQL schema-only dump has nine tables and eight primary keys, with no declared foreign keys. It also includes stored functions, indexes and ownership statements. Its header identifies PostgreSQL/pg_dump 16.14. This is supplied source material, not evidence that transactional workloads were executed. The paper describes foreign keys being added to the relational model after DDL extraction. The exact DDL-to-XMI conversion and additions still need a reproducible procedure. HammerDB data generation and loading instructions are pending.


File origins and versions are documented in the accompanying provenance metadata. Original SQL and XMI files are preserved.

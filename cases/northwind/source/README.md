# northwind source schemas

The XMI contains 14 tables, 92 columns, 14 key elements and 13 foreign-key elements. It matches the Northwind source in RoundTripV2/roundtrip/direct, ignoring line endings. The recovered DDL also matches cases/northwind/postgres/northwind_ddl.sql in the development repository, ignoring line endings.

The DDL is retained unchanged. Upstream attribution remains to be resolved: the development README names Yugabyte, while the paper cites pthom/northwind_psql. A tested PostgreSQL loading procedure is pending; in particular, review the legacy SET default_with_oids statement against the supported server. The fixed dataset and a statically checked loading procedure are now available in [../data/](../data/). The original DDL remains unchanged.

## Provenance

Files were copied byte-for-byte from recovered group OneDrive AWS material. See checksums.sha256 for integrity. Selection is based on the inspected repository and the final paper; filenames and folder dates alone do not establish the exact experimental software version. No database or migration has been executed during packaging.

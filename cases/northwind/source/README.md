# Northwind source schemas

The relational XMI contains 14 tables, 92 columns, 14 keys and 13 foreign keys. The original SQL DDL is retained alongside it.

Use the [PostgreSQL 16 loading procedure](../data/) for the fixed dataset; it handles the obsolete `SET default_with_oids` statement without modifying the original DDL. File provenance is documented in the accompanying metadata. The manuscript cites the PostgreSQL Northwind sample; the precise upstream revision of the supplied SQL has not been established.

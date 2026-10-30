# Northwind data preparation

## Contents

`northwind_data.sql` is an unchanged copy of the supplied AWS evaluation file. It matches the development repository data after normalizing line endings. Upstream attribution remains pending as explained in [source notes](../source/README.md).

`expected-counts.json` records row counts derived by statically inspecting the INSERT statements: 3,362 rows in total across 14 schema tables, including two empty tables. These match the recovered Northwind migration count report. Counts alone do not establish value-level migration equivalence.

## PostgreSQL 16 loading procedure

Use a fresh dedicated database called `northwind_reproduction` on an accessible PostgreSQL 16 server. The DDL drops and recreates the Northwind tables in the selected database. Do not use an existing research or production database.

From the repository root, with PostgreSQL client tools installed and connection settings configured (host, port and user), run:

```sh
createdb northwind_reproduction
psql -X --dbname=northwind_reproduction --set=ON_ERROR_STOP=1 --single-transaction --file=cases/northwind/data/load.sql
```

The load entry point resolves included files relative to itself. It loads the schema, loads the data, and checks every table count in one transaction. A SQL error or count mismatch fails the command and rolls back the transaction. Authentication and server startup must be configured separately; do not embed credentials in the scripts.

To check counts again:

```sh
psql -X --dbname=northwind_reproduction --set=ON_ERROR_STOP=1 --file=cases/northwind/data/validate-counts.sql
```

## Compatibility adjustment

The files under `postgresql16/` are derived from the preserved DDL and data files by removing only `SET default_with_oids = false;`, an obsolete setting. No table definition or INSERT statement was intentionally changed. Original files remain available for provenance. These are loading aids for the fixed Northwind instance, not the complete migration or query-equivalence experiment.

## Verification status

File integrity, SQL table names, inserted row counts, and the scope of the compatibility edit have been checked statically. The PostgreSQL loading commands have not yet been executed or validated against a running server. Successful counts will not by themselves prove schema correspondence or query-result equivalence.

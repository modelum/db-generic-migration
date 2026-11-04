# Northwind data preparation

The fixed dataset contains 3,362 rows across 14 tables, including two empty tables. The [PostgreSQL Northwind sample](https://github.com/pthom/northwind_psql) is cited in the paper.

## Files

- `postgresql16/`: schema and data SQL for PostgreSQL 16.
- `load.sql`: loads the schema and data, then validates table counts.
- `validate-counts.sql` and `expected-counts.json`: expected per-table counts and their validation.

## Loading

With PostgreSQL client tools installed, configure host, port and user, then run these commands from the repository root:

```sh
createdb northwind_reproduction
psql -X --dbname=northwind_reproduction --set=ON_ERROR_STOP=1 --single-transaction --file=cases/northwind/data/load.sql
```

Use a fresh dedicated database: the schema script drops and recreates tables. A SQL error or count mismatch rolls back the transaction.

To check counts again:

```sh
psql -X --dbname=northwind_reproduction --set=ON_ERROR_STOP=1 --file=cases/northwind/data/validate-counts.sql
```

The PostgreSQL 16 scripts omit the obsolete `SET default_with_oids = false` setting. Table definitions and data are otherwise unchanged. Row counts check population size; use the [query-equivalence experiment](../../../experiments/query-equivalence/) to compare migrated values.

-- psql entry point. Run only against a dedicated reproduction database.
-- The DDL drops and recreates the Northwind tables.
\set ON_ERROR_STOP on
\ir postgresql16/northwind_ddl.sql
\ir postgresql16/northwind_data.sql
\ir validate-counts.sql

# Data migrator

The [configurable executable](configurable-2026-10-30/) accepts the relational model basename and PostgreSQL/MongoDB connection parameters. [RUNNING.md](../../experiments/scaling/RUNNING.md) provides case-specific input names, commands and outputs, including canonical versus adapted Music Service.

[DataMigration](../source-projects/DataMigration/) contains the corresponding parameterized project update at revision `81179f6325dbdaea6c31205405939366b71bb824`. Dependencies are documented in [DEPENDENCIES.md](../source-projects/DEPENDENCIES.md). Source-to-binary identity has not been checked through an independent rebuild.

The supplied executable has been tested by its developer. Later metamodel revisions are a separate development task; they are not prerequisites for reproducing the evaluation implementation.

# Implementation projects

DataMigration uses development revision `81179f6325dbdaea6c31205405939366b71bb824`, with nine connection/model parameters. The other seven projects use revision `3d422b9df84dd66332d7bb855c717b5c3ace279b`. Per-project versions are listed in `SOURCES.md` at the repository root.

See [DEPENDENCIES.md](DEPENDENCIES.md) for build requirements and [migration instructions](../data-migrator/configurable-2026-10-30/) for the supplied executable. Dependency JARs are not all bundled with these source projects; see DEPENDENCIES.md.

## Project roles

| Project | Role |
| --- | --- |
| DataMigration | Data migration, forward schema transformations, and MusicService intermediate adaptation example |
| RoundTripV2 | Forward/reverse schema chain for Music Streaming, Northwind and TPC-C |
| Trace | Trace management and persistence; source for Trace.jar |
| OrionTraceChanger | U-Schema and trace updates; source for the trace updater JAR |
| TestSpeed | Flat-copy baseline for one relational table |
| um.modelum.documentMM | Generated Document metamodel runtime source |
| um.modelum.relationalMM | Generated Relational metamodel runtime source |
| um.modelum.uschemaMM | Generated U-Schema metamodel runtime source |

The active relational adapter is DataMigration/src/um/modelum/migration/adapter. Other adapter projects from the development repository are outside this active selection.


## Metamodel versions

The Ecores and runtime sources within these projects are retained with their project versions. The definitions under [metamodels](../metamodels/COMPATIBILITY.txt) include changes identified after the TPC-C experiments; adopting them is a subsequent implementation task, not a prerequisite to interpreting or reproducing the reported executions. See [version notes](../metamodels/COMPATIBILITY.txt).

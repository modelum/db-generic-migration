# Implementation projects

See [DEPENDENCIES.md](DEPENDENCIES.md) for build requirements and [migration instructions](../database-migrator/RUNNING.md) for the supplied executable. Dependency JARs are not all bundled with these source projects; see DEPENDENCIES.md.

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

The Ecores and runtime sources within these projects are retained with their project versions. The definitions in [metamodels](../metamodels/) match the implementation. Its `final-version/` subfolder contains later revisions that have not yet been incorporated into the code.

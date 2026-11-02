# Implementation projects

DataMigration uses revision `de23b5beb14dafe85561d84aa9b86799893f0a0b`, with nine connection/model parameters and no Oracle JDBC dependencies. RoundTripV2 retains its prior project version with only `um.modelum.migration.main.Principal.java` updated from the same revision to disable the trace-persistence test and its early exit. The other projects remain unchanged. See the repository [source/version notes](../../SOURCES.md).

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

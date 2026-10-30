# Migration source projects

These are unchanged source snapshots of the eight active projects identified by the maintainers, from the development repository. DataMigration is updated to commit 81179f6325dbdaea6c31205405939366b71bb824; the other seven projects retain commit 3d422b9df84dd66332d7bb855c717b5c3ace279b. Original Eclipse project names and tracked text files, generated Java sources and supporting models are preserved. The source checkout was not modified.

This is not a validated build or executable distribution. See [DEPENDENCIES.md](DEPENDENCIES.md) before importing or running a project. Original launchers contain local database configuration and experimental code; inspect their behavior before execution.

`provenance.json` records every included file and excluded JAR/ZIP/backup file. Binaries are inventoried rather than promoted as final dependencies. Binary archives and BAK directories were not copied.

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

## Metamodel distinction

The Ecore and generated runtime code inside these snapshots remain exactly as committed upstream. They are not silently replaced by the selected OneDrive Ecores in [../metamodels/](../metamodels/). The implementation alignment described in [COMPATIBILITY.txt](../metamodels/COMPATIBILITY.txt) must be completed before the combined package is considered runnable and consistent with those definitions. Supporting model snapshots are not independently validated expected outputs.

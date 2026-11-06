# Dependency map and build status

Use Eclipse with Java 21, EMF and Xtend. Assemble the libraries listed below before building the selected project.

## Intended project dependencies

| Consumer | Required project libraries | Other requirements |
| --- | --- | --- |
| DatabaseMigration | documentMM.jar, relationalMM.jar, uschemaMM.jar, Trace.jar, trace updater JAR | Xtend, EMF, PostgreSQL JDBC and MongoDB Java drivers |
| RoundTripV2 | documentMM.jar, relationalMM.jar, uschemaMM.jar, Trace.jar | Xtend and EMF |
| Trace | None of the application JARs | Xtend and EMF |
| OrionTraceChanger | documentMM.jar, relationalMM.jar, uschemaMM.jar, Trace.jar in its classpath | EMF |
| TestSpeed | None of the model/trace JARs | PostgreSQL JDBC and MongoDB Java drivers |
| Three um.modelum metamodel projects | Generated EMF runtime code | EMF/Eclipse dependencies |

Generate the metamodel runtimes and Trace.jar before their consumers. The trace updater is needed by DatabaseMigration; RoundTripV2 does not require it. TestSpeed can be built independently of the metamodel chain.

## Dependency configuration

- DatabaseMigration references PostgreSQL JDBC 42.7.10 and MongoDB Java driver 5.1.2.
- EMF dependencies are common 2.44.0, ecore 2.41.0 and ecore.xmi 2.39.0. Configure the corresponding Eclipse bundles or library paths.
- In Trace and OrionTraceChanger, replace the absolute `C:/eclipse2025-12/plugins/` paths in `.classpath` with the locations in your installation.
- DatabaseMigration expects the trace updater at `lib/OrionTraceChanger.jar`. Build the project libraries and place them at the paths specified by each `.classpath`; dependency JARs are not bundled with these source projects.
- Use Java 21 for the build. Some project manifests and classpaths still declare Java 17; align their execution environment with the selected Java 21 installation when configuring Eclipse.

## Execution configuration

DatabaseMigration accepts nine arguments: relational model basename, PostgreSQL host, port, user, password and database, then MongoDB host, port and database. It constructs a MongoDB connection without authentication arguments. See [configurable executable usage](../database-migrator/RUNNING.md). TestSpeed retains localhost/default-port and PostgreSQL music/music assumptions and accepts source database, target database and table. These are deployment settings, not a statement about the model independence of the approach.

DatabaseMigration writes intermediate/target models and `Migration_times.txt`. Its MusicService entry path intentionally contains a preliminary, predefined intermediate adaptation example, selected by the exact model basename `MusicService-relational-adaptation`; this is not a general adaptation-selection interface. The updated RoundTripV2 launcher comments out the trace-persistence test and its early exit, allowing the remaining forward/reverse chain to run. Outputs must be directed to a separate working location rather than overwriting preserved reference artifacts.

## Build preparation

Assemble the dependencies for the selected project/runtime versions and check the launchers before a new build. Later Ecore revisions belong to subsequent development and are not required to reproduce the earlier experiments. A build incorporating those changes must be identified separately from the evaluation implementation.

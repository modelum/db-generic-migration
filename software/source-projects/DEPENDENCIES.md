# Dependency map and build status

The map combines maintainer information with the recovered Eclipse project configuration. It is not a tested build recipe. No Java compilation, library regeneration or database execution was performed while copying these projects.

## Intended project dependencies

| Consumer | Required project libraries | Other requirements |
| --- | --- | --- |
| DataMigration | documentMM.jar, relationalMM.jar, uschemaMM.jar, Trace.jar, trace updater JAR | Xtend, EMF, PostgreSQL JDBC and MongoDB Java drivers |
| RoundTripV2 | documentMM.jar, relationalMM.jar, uschemaMM.jar, Trace.jar | Xtend and EMF |
| Trace | None of the application JARs | Xtend and EMF |
| OrionTraceChanger | documentMM.jar, relationalMM.jar, uschemaMM.jar, Trace.jar in its classpath | EMF |
| TestSpeed | None of the model/trace JARs | PostgreSQL JDBC and MongoDB Java drivers |
| Three um.modelum metamodel projects | Generated EMF runtime code | EMF/Eclipse dependencies |

Generate the metamodel runtimes and Trace.jar before their consumers. The trace updater is needed by DataMigration; RoundTripV2 does not require it. TestSpeed can be built independently of the metamodel chain.

## Recovered configuration details

- DataMigration references PostgreSQL JDBC 42.7.10 and MongoDB driver 5.1.2. Oracle JDBC dependencies have been removed from the updated DataMigration project and executable.
- The recovered EMF references include common 2.44.0, ecore 2.41.0 and ecore.xmi 2.39.0. Some projects use Eclipse bundle dependencies, others library paths.
- Trace and OrionTraceChanger have absolute paths to the original Eclipse installation. These need a portable dependency configuration.
- The updated DataMigration classpath expects `lib/OrionTraceChanger.jar`, replacing the earlier `TraceCanger.jar` spelling. Dependency JARs remain excluded from the source snapshot.
- Java declarations are mixed: DataMigration has JavaSE-21 in .classpath but JavaSE-17 in its manifest; the metamodel projects also mix 17 and 21. Select and test a consistent toolchain before documenting a supported build.
- JAR and ZIP files were not copied. Missing classpath files in this snapshot are expected until dependencies are assembled.

## Execution configuration

DataMigration accepts nine arguments: relational model basename, PostgreSQL host, port, user, password and database, then MongoDB host, port and database. It constructs a MongoDB connection without authentication arguments. See [configurable executable usage](../database-migrator/RUNNING.md). TestSpeed retains localhost/default-port and PostgreSQL music/music assumptions and accepts source database, target database and table. These are deployment settings, not a statement about the model independence of the approach.

DataMigration writes intermediate/target models and `Migration_times.txt`. Its MusicService entry path intentionally contains a preliminary, predefined intermediate adaptation example, selected by the exact model basename `MusicService-relational`; this is not a general adaptation-selection interface. The updated RoundTripV2 launcher comments out the trace-persistence test and its early exit, allowing the remaining forward/reverse chain to run. Outputs must be directed to a separate working location rather than overwriting preserved reference artifacts.

## Build preparation

Assemble the dependencies for the selected project/runtime versions and check the launchers before a new build. Later Ecore changes listed in COMPATIBILITY.txt belong to subsequent development and are not required to reproduce the earlier experiments. A build incorporating those changes must be identified separately from the evaluation implementation.

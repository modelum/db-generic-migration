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

- DataMigration references PostgreSQL JDBC 42.7.10 and MongoDB driver 5.1.2. The repository also inventories older Oracle dependencies; those are not required merely to reproduce the PostgreSQL case.
- The recovered EMF references include common 2.44.0, ecore 2.41.0 and ecore.xmi 2.39.0. Some projects use Eclipse bundle dependencies, others library paths.
- Trace and OrionTraceChanger have absolute paths to the original Eclipse installation. These need a portable dependency configuration.
- The DataMigration classpath expects `lib/TraceCanger.jar` (original spelling). Do not rename only one side of this dependency; align generated artifact names and consumers together.
- Java declarations are mixed: DataMigration has JavaSE-21 in .classpath but JavaSE-17 in its manifest; the metamodel projects also mix 17 and 21. Select and test a consistent toolchain before documenting a supported build.
- JAR and ZIP files were not copied. Their original repository paths, sizes and hashes remain in provenance.json. Missing classpath files in this snapshot are expected until dependencies are assembled.

## Execution configuration

DataMigration accepts the relational model basename, source database name and target database name. It uses localhost, default ports, PostgreSQL example credentials music/music and an unauthenticated MongoDB connection. TestSpeed uses the same local connection assumptions and accepts source database, target database and table. These are deployment settings, not a statement about the model independence of the approach.

DataMigration writes intermediate/target models and a migration timing report. Its MusicService entry path contains an intermediate adaptation example. RoundTripV2 contains a trace-persistence test that terminates the program early; the complete round-trip launcher must be reviewed before use. Outputs must be directed to a separate working location rather than overwriting preserved reference artifacts.

## Next build preparation

Confirm the selected Ecores and implement the documented alignment; regenerate matching EMF classes and JARs; make dependency paths portable; build Trace and the updater; build the migrator and schema chain; then run mapping/model checks and a small migration. Any resulting code changes and new outputs must be identifiable separately from these unchanged upstream snapshots.

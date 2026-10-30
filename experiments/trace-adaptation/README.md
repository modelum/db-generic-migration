# Trace-aware schema adaptation

## Reported evaluation

Section 6.3.4 of the author-supplied manuscript evaluates **TPC-C S**, exercising each supported operation separately: entity/feature renaming, attribute deletion and Aggregate-to-Reference conversion. It describes inspecting the adapted U-Schema and updated T1 trace, then rerunning U-Schema-to-Document without changing mapping rules.

The recovered material below is a **Music Streaming implementation example**, not the recovered record of that TPC-C evaluation. No TPC-C adaptation before/after models, traces or generated Document outputs have yet been identified in the inspected material.

## Available implementation example

- [DataMigration Principal.java](../../software/source-projects/DataMigration/src/um/modelum/migration/Principal.java) generates U-Schema and T1 from the relational input. When the first argument is exactly `MusicService-relational`, it saves/reloads `MusicService.trace`, calls the updater, saves/reloads the changed artifacts and continues to Document generation and data migration.
- [TraceChanger.java](../../software/source-projects/OrionTraceChanger/src/um/modelum/migration/TraceChanger.java) implements `disaggregate`, `rename`, `removeAttribute`, model saving and trace saving. [Trace](../../software/source-projects/Trace/) provides trace persistence.
- [Prueba.java](../../software/source-projects/OrionTraceChanger/src/um/modelum/migration/Prueba.java) is a standalone Music example with developer-specific absolute paths. It applies three operations in sequence: `disaggregate("app_user.playlist")`, `rename("app_user", "user_data")`, `removeAttribute("user_data.is_premium")`, then saves the model and trace.
- [Originales](../../software/source-projects/OrionTraceChanger/Originales/) and [Pruebas](../../software/source-projects/OrionTraceChanger/Pruebas/) each contain a relational model, U-Schema model and `MusicServiceTrace.tra`. Their directory names alone do not establish a complete before/after experimental record.

The updater saves over the model and trace paths supplied to it. Any future execution should use copies in a separate run directory to retain the original artifacts. DataMigration also writes models next to its JAR, while the literal relative trace filename is resolved from the process working directory. The existing localhost connections and build/metamodel requirements are described in [software source documentation](../../software/source-projects/README.md).

## Packaging checks and remaining evidence

This documentation comes from static inspection of the preserved source and manuscript; no adaptation, compilation or database migration was executed. No executable TPC-C adaptation command or successful rerun is claimed. To recover the reported experiment, identify the exact affected TPC-C element for each operation and any retained initial/adapted U-Schema, initial/updated T1 and regenerated Document models. The operations in the Music example should not be substituted for those experiment records.

# Trace-aware schema adaptation

## Manuscript clarification

**The adaptation evaluation was performed on Music Service (Music Streaming), not TPC-C S.** Entity/feature renaming, attribute deletion and Aggregate-to-Reference conversion were tested individually and in sequence. References to TPC-C in the adaptation-configuration paragraph of Section 6.1, Section 6.3.4 and the adaptation-related external-validity discussion of the author-supplied manuscript are a case-study attribution error. This clarification applies to the manuscript snapshot identified in [results provenance](../../results/provenance.json); it does not assert the same wording in every public preprint revision.

## Recovered sequential execution artifacts

[recovered-music-sequence/](recovered-music-sequence/) preserves the relational input, adapted U-Schema, generated Document model, generated Mongoose schema and updated T1 trace recovered from `aws-instance.zip`. Original filenames and bytes are retained; [provenance.json](provenance.json) records archive membership and hashes, and [checksums.sha256](checksums.sha256) records file integrity.

The implementation applies these operations in sequence:

1. `disaggregate("app_user.playlist")`: convert the playlist aggregate into a reference, making playlist independent.
2. `rename("app_user", "user_data")`.
3. `removeAttribute("user_data.is_premium")`.

The recovered models and trace reflect the combined outcome: `user_data`, no `is_premium` feature on that entity, and an independent `playlist` Document collection. These are recovered artifacts, not newly generated results. The files included here document the sequential execution.

## Implementation and executable identification

The current prototype integrates a predefined Music Service adaptation directly into the migrator. It activates when the model basename is exactly `MusicService-relational` (case-sensitive, without `.xmi`). This is a preliminary demonstration of trace-aware adaptation, not a general user-facing interface for selecting arbitrary adaptations. Connection parameterization is independent of this model-name trigger. The recovered sequential outputs document this predefined scenario.

- [DataMigration Principal.java](../../software/source-projects/DataMigration/src/um/modelum/migration/Principal.java) generates U-Schema/T1. For first argument `MusicService-relational`, it saves/reloads `MusicService.trace`, applies the sequence, saves/reloads the changed artifacts, and continues to Document generation and data migration.
- [TraceChanger.java](../../software/source-projects/OrionTraceChanger/src/um/modelum/migration/TraceChanger.java) implements the operations and persistence; [Trace](../../software/source-projects/Trace/) supplies trace support.
- [Prueba.java](../../software/source-projects/OrionTraceChanger/src/um/modelum/migration/Prueba.java) is a standalone Music sequence example with developer-specific paths. [Originales](../../software/source-projects/OrionTraceChanger/Originales/) and [Pruebas](../../software/source-projects/OrionTraceChanger/Pruebas/) preserve additional Music models/traces; directory names alone do not establish before/after status.

The recovered archive contains three distinct JARs. Static inspection of the entry-point class finds the adaptation calls in `migratoru.jar`, but not in `migrator.jar` or `migrator5000.jar`. The archive's `migrator.jar` is byte-identical to the earlier recovered AWS copy. JAR hashes are inventoried in provenance; binaries are not added here and their correspondence to a particular source commit has not been established. No JAR was executed.

## Execution context and checks

The updater overwrites the model and trace paths supplied to it; future execution should use copies in a separate run directory. DataMigration writes models next to the JAR, while its literal relative trace filename resolves from the working directory. Existing connection assumptions and build/metamodel requirements remain as described in [software documentation](../../software/source-projects/README.md); this packaging changes no software behavior.

Packaging checks cover XML parsing, byte integrity and inspection of adaptation markers in models, trace and entry-point classes. No compilation, adaptation, database migration or EMF comparison was performed. The archive's Northwind timing file and unrelated query output are not presented as adaptation results.

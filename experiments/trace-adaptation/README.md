# Trace-aware schema adaptation

## Objective

Evaluate whether changes to the intermediate U-Schema model can be propagated to the transformation trace and subsequent Document schema without changing the mapping rules. The case study is Music Streaming.

Three operations were evaluated individually and in sequence:

1. Convert the `app_user.playlist` aggregate into a reference, making playlist independent.
2. Rename `app_user` to `user_data`.
3. Remove `user_data.is_premium`.

## Experiment artifacts

The files in [music-service-sequence/](music-service-sequence/) document the sequential application of the three operations:

| File | Role |
| --- | --- |
| [MusicService-relational.xmi](music-service-sequence/MusicService-relational.xmi) | Relational input schema |
| [MusicService-relational-uschema.xmi](music-service-sequence/MusicService-relational-uschema.xmi) | Adapted intermediate U-Schema |
| [MusicService.trace](music-service-sequence/MusicService.trace) | Updated relational-to-U-Schema trace T1 |
| [MusicService-relational-document.xmi](music-service-sequence/MusicService-relational-document.xmi) | Document schema generated from the adapted U-Schema |
| [MusicService-relational-mongoose.js](music-service-sequence/MusicService-relational-mongoose.js) | Generated Mongoose schema |

The supplied models and trace show the result of applying all three operations in sequence.

## Execution

The [DatabaseMigration implementation](../../software/source-projects/DatabaseMigration/) integrates this predefined adaptation into the migration chain. It is activated by the exact, case-sensitive model basename `MusicService-relational`, without `.xmi`. This is a preliminary prototype for the demonstrated scenario; it does not expose a general interface for selecting arbitrary adaptations. Connection parameters are independent of the adaptation trigger.

See the [configurable migrator instructions](../../software/database-migrator/RUNNING.md) for prerequisites, the nine connection/model arguments and invocation. Use a separate working directory with copies of the input artifacts: the program writes intermediate models and updates the trace during execution. The documented Music Streaming invocation continues from the adapted schema to Document generation and data migration.

## Inspection

Inspect the adapted U-Schema, updated relational-to-U-Schema trace and generated Document schema together. The sequential result has:

- An entity named `user_data` instead of `app_user`.
- No `is_premium` attribute on `user_data`.
- A reference to independent playlists instead of the original playlist aggregate.
- Corresponding updated trace entries and Document structure.

These artifacts concern the adapted scenario. The canonical query Q1 uses embedded playlists and belongs to the separate [query-equivalence experiment](../query-equivalence/).

## Manuscript clarification

The adaptation evaluation used **Music Streaming**, not TPC-C S. The TPC-C attribution in the adaptation-configuration paragraph of Section 6.1, Section 6.3.4 and the adaptation-related external-validity discussion is an error in the manuscript. The operations were tested individually and sequentially.

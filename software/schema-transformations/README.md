# Schema transformations

[DataMigration](../source-projects/DataMigration/) implements the forward Relational-to-U-Schema-to-Document chain. [RoundTripV2](../source-projects/RoundTripV2/) contains the forward and inverse schema transformations. [Trace](../source-projects/Trace/) manages the correspondences.

See [schema validation](../../experiments/schema-validation/) and [round-trip instructions](../../experiments/roundtrip/) for inputs, output pairs and inspection. The supplied RoundTripV2 launcher stops after a trace-persistence test; the guide identifies that execution boundary. [DEPENDENCIES.md](../source-projects/DEPENDENCIES.md) lists build requirements.

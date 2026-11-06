# Running relational-to-document migration

## Requirements

Use Java 21 or later and the [migrator](migrator.jar). Prepare PostgreSQL and MongoDB using the [environment](../../environment/) and [data instructions](../../environment/DATA-PREPARATION.md).

Create a separate working directory for each case/size and copy `migrator.jar` and the selected relational XMI into it. Run the command from that directory. Model paths are resolved beside the JAR and the trace/timing report uses the working directory. Use a dedicated empty target for a new experiment and preserve each run's outputs before reusing a directory. The migrator is not a source-database loader.

## Model selection

The first three scenarios perform canonical migration. The adaptation scenario uses the same Music Streaming relational model under a different filename to activate the predefined U-Schema adaptation.

| Scenario | Input model |
| --- | --- |
| Canonical migration with Music Streaming | [MusicService-relational.xmi](../../cases/music-streaming/source/MusicService-relational.xmi) |
| Canonical migration with TPC-C | [TPC-C-relational-sinPK.xmi](../../cases/tpcc/source/TPC-C-relational-sinPK.xmi) |
| Canonical migration with Northwind | [Northwind-relational.xmi](../../cases/northwind/source/Northwind-relational.xmi) |
| Adaptation with Music Streaming. | [MusicService-relational-adaptation.xmi](../../cases/music-streaming/source/MusicService-relational-adaptation.xmi) |

The code checks the exact basename `MusicService-relational-adaptation` to activate adaptation; it does not use a separate mode argument.

## Command

```sh
java -jar migrator.jar MODEL_BASENAME PG_HOST PG_PORT PG_USER PG_PASSWORD PG_DATABASE MONGO_HOST MONGO_PORT MONGO_DATABASE
```

`MODEL_BASENAME` is the name of the relational XMI file **without the `.xmi` extension**. For example, use `MusicService-relational` for `MusicService-relational.xmi`. Place the file in the same directory as `migrator.jar`.

The command takes nine arguments, in this order:

| Parameter | Meaning |
| --- | --- |
| `MODEL_BASENAME` | Relational model filename without `.xmi` |
| `PG_HOST` | PostgreSQL server hostname or IP address |
| `PG_PORT` | PostgreSQL port, usually `5432` |
| `PG_USER` | PostgreSQL username |
| `PG_PASSWORD` | PostgreSQL password |
| `PG_DATABASE` | Source PostgreSQL database name |
| `MONGO_HOST` | MongoDB server hostname or IP address |
| `MONGO_PORT` | MongoDB port, usually `27017` |
| `MONGO_DATABASE` | Target MongoDB database name |

Replace the placeholders with your model name and connection settings. This interface does not include MongoDB username/password arguments. Quote any argument containing spaces or shell-special characters as required by your shell.

For example, with databases prepared separately and illustrative connection values:

```sh
java -jar migrator.jar MusicService-relational localhost 5432 music music musicdb-S localhost 27017 MusicService-S
java -jar migrator.jar TPC-C-relational-sinPK localhost 5432 music music tpcc-S localhost 27017 TPC-C-S
java -jar migrator.jar Northwind-relational localhost 5432 music music northwind localhost 27017 Northwind
```

Run only the command for the input/database pair prepared in that working directory. For M/L, select the corresponding populated source and a distinct target. For adaptation use `MusicService-relational-adaptation` and a distinct target, for example `MusicService-S-Updated`; see [adaptation](../../experiments/trace-adaptation/).

## Outputs and inspection

The entry point writes `MODEL_BASENAME-uschema.xmi`, `MODEL_BASENAME-document.xmi`, `MODEL_BASENAME-mongoose.js` and `Migration_times.txt`. The adaptation branch additionally persists and updates `MusicService.trace`. Model and timing files can be overwritten on a subsequent run.

Inspect completion/error output, generated models and per-table counts in the timing report; inspect the migrated collections using the intended canonical/adapted representation. Save the command with password redacted, case/size, software version, source counts, target name, model outputs and report for that run.

The paper times the data-migration stage rather than the whole Java process. Compare the appropriate report measurements with [Tables 9/10](../../results/scaling/); do not compare total process wall time as though it had the same scope. The reported insert batch is 1,000. Execution times depend on the hardware and runtime environment.

# Running relational-to-document migration

## Requirements

Use Java 21 or later and the [configurable migrator](../../software/data-migrator/configurable-2026-10-30/). Prepare PostgreSQL and MongoDB using the [environment](../../environment/) and [data instructions](../../environment/DATA-PREPARATION.md). The developer reports testing this executable; no additional database run was performed while preparing this guide.

Create a separate working directory for each case/size and copy `migrator.jar` and the selected relational XMI into it. Run the command from that directory. Model paths are resolved beside the JAR and the trace/timing report uses the working directory. Use a dedicated empty target for a new experiment and preserve each run's outputs before reusing a directory. The migrator is not a source-database loader.

## Model selection

Both Music scenarios start from the same [original relational model](../../cases/music-streaming/source/MusicService-relational.xmi). For canonical migration, copy it into the working directory and rename that copy to `MusicService-canonical.xmi`. For adaptation, keep the name `MusicService-relational.xmi`. The filename and first argument used for execution are shown separately below.

| Scenario | Input model | 
| --- | --- | 
| Music with adaptation |[MusicService-relational.xmi](../../cases/tpcc/source/MusicService-relational.xmi) | 
| Music without adaptation | Same Music model but the file must be renamed; | 
| TPC-C | [TPC-C-relational-sinPK.xmi](../../cases/tpcc/source/TPC-C-relational-sinPK.xmi) | 
| Northwind | [Northwind-relational.xmi](../../cases/northwind/source/Northwind-relational.xmi) | 

The code checks the exact basename `MusicService-relational` to activate adaptation; it does not use a separate mode argument. 

## Command

```sh
java -jar migrator.jar MODEL_BASENAME PG_HOST PG_PORT PG_USER PG_PASSWORD PG_DATABASE MONGO_HOST MONGO_PORT MONGO_DATABASE
```

Replace all placeholders with the actual connection settings. PostgreSQL credentials are arguments; MongoDB host/port/database are configurable but there are no MongoDB authentication arguments. Do not publish actual passwords in a run record.

For example, with databases prepared separately and illustrative connection values:

```sh
java -jar migrator.jar MusicService-canonical localhost 5432 music music musicdb-S localhost 27017 MusicService-S
java -jar migrator.jar TPC-C-relational-sinPK localhost 5432 music music tpcc-S localhost 27017 TPC-C-S
java -jar migrator.jar Northwind-relational localhost 5432 music music northwind localhost 27017 Northwind
```

Run only the command for the input/database pair prepared in that working directory. For M/L, select the corresponding populated source and a distinct target. For adaptation use `MusicService-relational` and a distinct target, for example `MusicService-S-Updated`; see [adaptation](../trace-adaptation/).

## Outputs and inspection

The entry point writes `MODEL_BASENAME-uschema.xmi`, `MODEL_BASENAME-document.xmi`, `MODEL_BASENAME-mongoose.js` and `Migration_times.txt`. The adaptation branch additionally persists and updates `MusicService.trace`. Model and timing files can be overwritten on a subsequent run.

Inspect completion/error output, generated models and per-table counts in the timing report; inspect the migrated collections using the intended canonical/adapted representation. Save the command with password redacted, case/size, software version, source counts, target name, model outputs and report for that run.

The paper times the data-migration stage rather than the whole Java process. Compare the appropriate report measurements with [Tables 9/10](../../results/scaling/); do not compare total process wall time as though it had the same scope. The reported insert batch is 1,000. A new configurable execution is a new run, not a reconstruction of the exact original measurement environment.

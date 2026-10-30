# Configurable data migrator

This newly supplied executable accepts connection parameters. It is distinct from the recovered AWS executables used in earlier artifact collection; it is not identified as the binary used for the paper's historical timing measurements. The associated updated source commit has not yet been supplied. [provenance.json](provenance.json) identifies the unchanged binary by SHA-256.

## Prerequisites and invocation

Use Java 21 or later (entry-point class-file version 65), accessible PostgreSQL and MongoDB servers, a prepared relational source database and the matching relational XMI model. The executable distribution has not been run during packaging.

Place a **copy** of the input XMI next to the JAR in a separate working directory. Run from that directory so the model outputs and relative trace location remain together. The first argument is the model filename without `.xmi`.

```sh
java -jar migrator.jar MODEL_BASENAME PG_HOST PG_PORT PG_USER PG_PASSWORD PG_DATABASE MONGO_HOST MONGO_PORT MONGO_DATABASE
```

| Argument | Meaning |
| --- | --- |
| 1 | Relational XMI basename, without extension |
| 2 | PostgreSQL host/IP |
| 3 | PostgreSQL port |
| 4 | PostgreSQL user |
| 5 | PostgreSQL password |
| 6 | PostgreSQL source database |
| 7 | MongoDB host/IP |
| 8 | MongoDB port |
| 9 | MongoDB target database |

Example supplied with the executable:

```sh
java -jar migrator.jar MusicService-relational localhost 5432 user user_pwd sourceDatabase localhost 27017 targetDatabase
```

Replace example connection values with the intended configuration. Use a dedicated target for a new reproduction and preserve reference artifacts separately. This interface supplies PostgreSQL credentials but no MongoDB username/password arguments.

## Music Service behavior

**The example above activates schema adaptation.** When argument 1 is exactly `MusicService-relational`, the entry point applies the Music adaptation sequence: disaggregate playlist, rename app_user to user_data and remove is_premium. It saves/reloads `MusicService.trace` and continues migration from the adapted model. It therefore does not represent the canonical embedded-playlist Q1 scenario. No separate adaptation-enable/disable argument is present in this interface; a supported canonical Music invocation must be confirmed before treating this binary as its reproduction command.

The program writes intermediate U-Schema, Document and Mongoose artifacts using the model basename next to the JAR. Keep copies of the original input and any traces; adaptation saves over its intermediate model/trace paths. See [adaptation](../../../experiments/trace-adaptation/) for recovered outputs and experiment scope.

## Verification boundary

The JAR archive CRC check passed, the entry-point class version and argument handling were inspected, and its Music adaptation condition was checked statically. No database connection, data migration or help invocation was executed. Runtime/dependency compatibility, metamodel alignment, exact source correspondence and canonical-case execution remain to be verified. The new connection interface does not by itself establish that the previously documented metamodel differences have been resolved.

# Music Streaming data generation

## Files

- `generate-data-music-original.py`: unchanged generator recovered from the development repository, with its original earlier dataset parameters.
- `dataset_config-paper.json`: S/M/L configuration reconstructed from Table 6 of the final paper.
- `generate-data-music-paper.py`: a prepared copy changing only DATASET_CONFIG to those reconstructed parameters. The generation logic, connection settings, random seed and insertion batch size remain unchanged.
- `paper-row-counts.json`: the source table counts reported in Table 6, including the observed song_style counts.

The evaluation used this generator with DATASET_CONFIG adjusted on AWS. The exact AWS script was not recovered; the supplied paper configuration is an explicit reconstruction, not a claim of byte-identical recovery. Source commit, original path and file hashes are recorded in provenance.json.

## Reconstructed parameters

| Parameter | S | M | L |
| --- | ---: | ---: | ---: |
| users | 1,000 | 10,000 | 100,000 |
| songs | 5,000 | 50,000 | 500,000 |
| styles | 25 | 25 | 25 |
| playlists_per_user | 10 | 10 | 10 |
| songs_per_playlist | 20 | 20 | 20 |
| listens_per_user | 50 | 50 | 50 |
| recent_songs | 10 | 10 | 10 |

## Preparation and invocation

Python and psycopg2 are required; psycopg2-binary is one installation option. Exact dependency versions for the original execution have not been fixed in this package.

Prepare a dedicated PostgreSQL database with the Music Streaming schema before running the generator. Review [source notes](../../README.md#music-streaming): the preserved SQL contains setup statements that are not a ready-to-run PostgreSQL loading procedure.

The script keeps DB_CONFIG from the original: localhost:5432, database musicdb, user music and password music. Change those connection settings in a separate working copy when preparing each named S/M/L database; the size argument selects dataset parameters, not a database name. The generator truncates its target tables before inserting, so do not point it at an existing database that must be preserved.

Once the dedicated schema and connection settings are prepared, invoke the working copy with one size argument:

```sh
python generate-data-music-paper.py S
```

Use M or L instead for the other instances, preserving them in separate databases when needed. The script commits the inserts on completion. It does not create the database or run the migrator.

## Randomness and row counts

The original RANDOM_SEED remains 42. UUID generation and set iteration are not fully controlled by this Python random seed. Therefore identical identifiers, complete row contents, recent-song ordering or query parameters from the original AWS instances are not guaranteed by rerunning this script.

song_style has one to three randomly chosen styles per song. Table 6 reports 10,046, 100,021 and 999,945 rows for S/M/L; these are observed counts, not direct configuration parameters. Check the generated counts against the paper, recording any differences rather than editing data or claiming an exact match in advance. Other table counts are determined by the size parameters and fixed per-user/per-playlist counts.

The unchanged BATCH_SIZE=5,000 controls source-data population. It is separate from the data migrator's batch size of 1,000 documents reported in the migration experiments.

## Verification status

Both Python files were parsed statically; all statements outside DATASET_CONFIG have identical ASTs. Copy integrity and parameter-derived counts were checked. No generator, PostgreSQL load or migration was executed. Reproduction and dependency validation remain pending.

## Recording a prepared instance

After generation, record row counts without changing the data:

```sh
psql -X --dbname=YOUR_MUSIC_DATABASE --set=ON_ERROR_STOP=1 --file=cases/music-streaming/data/count-source-rows.sql
```

Run from the repository root and replace the database name with the dedicated source instance; configure host/user authentication separately. The query reads the eight tables in `public`. Compare its output with `paper-row-counts.json`, retaining the actual counts, selected size and generator-copy hash as a new run record. Random song_style counts can differ. This helper is a new packaging aid, not an original evaluation output, and has not been run against PostgreSQL.

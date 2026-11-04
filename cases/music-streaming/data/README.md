# Music Streaming data generation

## Files

- `generate-data-music-paper.py`: data generator configured with the S/M/L parameters in Table 6.
- `dataset_config-paper.json`: generation parameters in JSON format.
- `paper-row-counts.json`: observed table counts reported in Table 6.
- `count-source-rows.sql`: query for checking populated table counts.

## Generation parameters

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

Python and psycopg2 are required. Prepare a dedicated PostgreSQL database using the [source schema](../source/). Run database-creation statements separately and omit `USE DATABASE`, which is not a PostgreSQL command.

Set `DB_CONFIG` in a working copy of the generator to the intended host, port, database and credentials. The size argument selects generation parameters, not a database name. The generator truncates its target tables before inserting data, so use a dedicated database.

```sh
python generate-data-music-paper.py S
```

Use M or L for the other sizes. The script populates the existing schema; it does not create the database or run the migrator.

## Row counts

The random seed is 42, but UUIDs are generated separately. Identifiers and randomly generated `song_style` counts can differ between runs. Table 6 reports observed counts, rather than fixed targets for these random values. Select query parameters from the instance being tested.

The generator's batch size of 5,000 is independent of the migration batch size of 1,000.

After generation, optionally check the eight source tables from the repository root:

```sh
psql -X --dbname=YOUR_MUSIC_DATABASE --set=ON_ERROR_STOP=1 --file=cases/music-streaming/data/count-source-rows.sql
```

Configure host/user authentication for the intended database and compare with `paper-row-counts.json`, allowing for the random `song_style` count. This is a preparation check, not a result of the paper's migration experiments.

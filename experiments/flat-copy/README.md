# Flat-copy baseline

The baseline copies each relational table into its own MongoDB collection, without U-Schema mappings, embedding or reference reconstruction. The paper reports L-instance elapsed times of 370 seconds for Music Streaming and 774 seconds for TPC-C, using batches of 1,000.

## Implementation and invocation

[TestSpeed](../../software/source-projects/TestSpeed/) implements a single-table copy. Its entry point is `Speed`; the source uses localhost, standard database ports, PostgreSQL music/music credentials and unauthenticated MongoDB. It accepts source database, target database and table name.

After assembling the dependencies and exporting the TestSpeed project to a runnable `testSpeed.jar`, run:

```sh
java -jar testSpeed.jar SOURCE_DATABASE TARGET_DATABASE TABLE_NAME
```

A rebuilt baseline executable is not supplied in this folder; see [dependencies](../../software/source-projects/DEPENDENCIES.md). Use a dedicated target with none of the table-named collections already present, since the launcher creates each collection. Execute once per table against the L source, retaining each console output.

| Case | Tables to copy |
| --- | --- |
| Music Streaming | app_user, song, musical_style, song_style, playlist, playlist_song, listening, most_recent_song |
| TPC-C | warehouse, district, customer, history, item, stock, orders, new_order, order_line |

Each invocation copies one complete table; no relationship-based ordering is required for this baseline. Record per-table rows and times and the method used to combine timings. The original Music displayed times are rounded per-table values summing to 370 seconds, not evidence of a single global timer. Keep that distinction when comparing a new aggregate measurement.

## Results

[results/flat-copy](../../results/flat-copy/) contains both reported baseline values and the Music L per-table count/timing breakdown, with source metadata. The original TPC-C console output is not included.

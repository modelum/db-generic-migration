# Music Streaming queries

SQL/MongoDB query pairs corresponding to the access patterns in Table 11.

| ID | Recovered purpose | Files |
| --- | --- | --- |
| Q1 | Playlists and songs of a selected user | [Q1.sql](Q1.sql), [Q1.js](Q1.js) |
| Q2 | Styles associated with a selected song | [Q2.sql](Q2.sql), [Q2.js](Q2.js) |
| Q3 | Count of users satisfying a listening-count condition | [Q3.sql](Q3.sql), [Q3.js](Q3.js) |

## Use

Use the actual source database and its migrated counterpart, with matching filter parameters and schema representation. The JavaScript uses `db(...).collection(...)` client notation. [Comparison instructions](../../../experiments/query-equivalence/COMPARISON.md) describe field correspondence, duplicates, dates and numeric values for a new reproduction.

Q1 uses canonical embedded playlists. The adaptation scenario instead uses independent playlists and is documented separately.

Query revisions and source-document references are recorded in `provenance.json` and Git history. The [reported results](../../../results/query-equivalence/) are distinct from any new-run query outputs. These scripts were not executed during package preparation.

# TPC-C queries

SQL/MongoDB query pairs corresponding to the access patterns in Table 11.

| ID | Recovered purpose | Files |
| --- | --- | --- |
| Q7 | Customer balances and order-line counts by district | [Q7.sql](Q7.sql), [Q7.js](Q7.js) |
| Q8 | Payment-history entries for selected customers in a district and warehouse | [Q8.sql](Q8.sql), [Q8.js](Q8.js) |
| Q9 | Items below a stock threshold with names and prices | [Q9.sql](Q9.sql), [Q9.js](Q9.js) |

## Use

Use the actual source database and its migrated counterpart, with matching filter parameters and schema representation. The JavaScript uses `db(...).collection(...)` client notation. [Comparison instructions](../../../experiments/query-equivalence/COMPARISON.md) describe field correspondence, duplicates, dates and numeric values for a new reproduction.

Database names in the scripts must correspond to the intended instance.

Query revisions and source-document references are recorded in `provenance.json` and Git history. The [reported results](../../../results/query-equivalence/) are distinct from any new-run query outputs. These scripts were not executed during package preparation.

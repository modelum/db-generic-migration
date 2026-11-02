# Northwind queries

SQL/MongoDB query pairs corresponding to the access patterns in Table 11.

| ID | Recovered purpose | Files |
| --- | --- | --- |
| Q4 | Customers and order dates for a selected shipper | [Q4.sql](Q4.sql), [Q4.js](Q4.js) |
| Q5 | Customer, products and categories for a selected order | [Q5.sql](Q5.sql), [Q5.js](Q5.js) |
| Q6 | Managers of employees involved in a selected customer orders; recovered version also filters order date | [Q6.sql](Q6.sql), [Q6.js](Q6.js) |

## Use

Use the actual source database and its migrated counterpart, with matching filter parameters and schema representation. The JavaScript uses `db(...).collection(...)` client notation. [Comparison instructions](../../../experiments/query-equivalence/COMPARISON.md) describe field correspondence, duplicates, dates and numeric values for a new reproduction.

Database names in the scripts must correspond to the intended instance.

Query revisions are recorded in Git history; see the common source/version notes in `SOURCES.md` at the repository root. The [reported results](../../../results/query-equivalence/) are distinct from any new-run query outputs. These scripts were not executed during package preparation.

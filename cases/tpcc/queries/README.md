# tpcc recovered queries

These are candidate SQL/MongoDB query pairs recovered from evaluation documents and associated with the query purposes in Table 11. Some queries have since been revised in the repository. The current suite has not been executed or validated during packaging as the final evaluation suite. IDs identify the intended paper correspondence, not proof that this exact text produced the published result.

| ID | Recovered purpose | Files |
| --- | --- | --- |
| Q7 | Customer balances and order-line counts by district | [Q7.sql](Q7.sql), [Q7.js](Q7.js) |
| Q8 | Payment-history entries for selected customers in a district and warehouse | [Q8.sql](Q8.sql), [Q8.js](Q8.js) |
| Q9 | Items below a stock threshold with names and prices | [Q9.sql](Q9.sql), [Q9.js](Q9.js) |

## Provenance and use

Original evaluation documents and alternate drafts are preserved outside this public repository. `provenance.json` identifies the source document, its hash and section, the original extracted-file hash, and the current file hash. Initial extraction joined Word paragraph text with LF line endings. For subsequently revised files, revision commit IDs distinguish the current query from that initial extraction; `checksums.sha256` describes the current files.

The JavaScript retains the original database-client notation db(...).collection(...); it is not presented as a tested mongosh command. Database names, example identifiers, formatting and query expressions may have been revised; consult the recorded commits. Check the intended database, generated field names, parameters and client dialect before execution.

SQL/MongoDB result normalization, comparison outputs and the final selection are not supplied by these files. Do not infer query equivalence from their presence in the package.

## Repository revisions

Q7-Q9 JavaScript use the `TPC-C-S` database name; Q8 SQL formatting was revised. These names are execution parameters, not evidence of a new query-equivalence run.

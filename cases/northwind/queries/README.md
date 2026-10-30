# northwind recovered queries

These are candidate SQL/MongoDB query pairs recovered from evaluation documents and associated with the query purposes in Table 11. Some queries have since been revised in the repository. The current suite has not been executed or validated during packaging as the final evaluation suite. IDs identify the intended paper correspondence, not proof that this exact text produced the published result.

| ID | Recovered purpose | Files |
| --- | --- | --- |
| Q4 | Customers and order dates for a selected shipper | [Q4.sql](Q4.sql), [Q4.js](Q4.js) |
| Q5 | Customer, products and categories for a selected order | [Q5.sql](Q5.sql), [Q5.js](Q5.js) |
| Q6 | Managers of employees involved in a selected customer orders; recovered version also filters order date | [Q6.sql](Q6.sql), [Q6.js](Q6.js) |

## Provenance and use

Original evaluation documents and alternate drafts are preserved outside this public repository. `provenance.json` identifies the source document, its hash and section, the original extracted-file hash, and the current file hash. Initial extraction joined Word paragraph text with LF line endings. For subsequently revised files, revision commit IDs distinguish the current query from that initial extraction.

The JavaScript retains the original database-client notation db(...).collection(...); it is not presented as a tested mongosh command. Database names, example identifiers, formatting and query expressions may have been revised; consult the recorded commits. Check the intended database, generated field names, parameters and client dialect before execution.

SQL/MongoDB result normalization, comparison outputs and the final selection are not supplied by these files. Do not infer query equivalence from their presence in the package.

## Repository revisions

Q4-Q6 JavaScript use the `Northwind` database name; some explanatory comments were also revised. The client notation and date/value handling still require the matching execution environment.

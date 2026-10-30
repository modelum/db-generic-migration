# northwind recovered queries

These are candidate SQL/MongoDB query pairs recovered from evaluation documents and associated with the query purposes in Table 11. They have not been corrected, executed or validated as the final query suite. IDs identify the intended paper correspondence, not proof that this exact text produced the published result.

| ID | Recovered purpose | Files |
| --- | --- | --- |
| Q4 | Customers and order dates for a selected shipper | [Q4.sql](Q4.sql), [Q4.js](Q4.js) |
| Q5 | Customer, products and categories for a selected order | [Q5.sql](Q5.sql), [Q5.js](Q5.js) |
| Q6 | Managers of employees involved in a selected customer orders; recovered version also filters order date | [Q6.sql](Q6.sql), [Q6.js](Q6.js) |

## Provenance and use

Original evaluation documents and alternate drafts are preserved outside this public repository. provenance.json identifies the document, its hash, the section and the extracted file hash. Extraction joins Word paragraph text with LF line endings; query statements, comments, aliases, field names, operators and example parameter values have not been changed.

The JavaScript retains the original database-client notation db(...).collection(...); it is not presented as a tested mongosh command. Original database names and example identifiers remain unchanged. Check the intended database, generated field names, parameters and client dialect before execution.

SQL/MongoDB result normalization, comparison outputs and the final selection are not supplied by these files. Do not infer query equivalence from their presence in the package.

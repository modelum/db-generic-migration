# music-streaming recovered queries

These are candidate SQL/MongoDB query pairs recovered from evaluation documents and associated with the query purposes in Table 11. They have not been corrected, executed or validated as the final query suite. IDs identify the intended paper correspondence, not proof that this exact text produced the published result.

| ID | Recovered purpose | Files |
| --- | --- | --- |
| Q1 | Playlists and songs of a selected user | [Q1.sql](Q1.sql), [Q1.js](Q1.js) |
| Q2 | Styles associated with a selected song | [Q2.sql](Q2.sql), [Q2.js](Q2.js) |
| Q3 | Count of users satisfying a listening-count condition | [Q3.sql](Q3.sql), [Q3.js](Q3.js) |

## Provenance and use

Original evaluation documents and alternate drafts are preserved outside this public repository. provenance.json identifies the document, its hash, the section and the extracted file hash. Extraction joins Word paragraph text with LF line endings; query statements, comments, aliases, field names, operators and example parameter values have not been changed.

The JavaScript retains the original database-client notation db(...).collection(...); it is not presented as a tested mongosh command. Original database names and example identifiers remain unchanged. Check the intended database, generated field names, parameters and client dialect before execution.

SQL/MongoDB result normalization, comparison outputs and the final selection are not supplied by these files. Do not infer query equivalence from their presence in the package.

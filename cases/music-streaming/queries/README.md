# music-streaming recovered queries

These are candidate SQL/MongoDB query pairs recovered from evaluation documents and associated with the query purposes in Table 11. Some queries have since been revised in the repository. The current suite has not been executed or validated during packaging as the final evaluation suite. IDs identify the intended paper correspondence, not proof that this exact text produced the published result.

| ID | Recovered purpose | Files |
| --- | --- | --- |
| Q1 | Playlists and songs of a selected user | [Q1.sql](Q1.sql), [Q1.js](Q1.js) |
| Q2 | Styles associated with a selected song | [Q2.sql](Q2.sql), [Q2.js](Q2.js) |
| Q3 | Count of users satisfying a listening-count condition | [Q3.sql](Q3.sql), [Q3.js](Q3.js) |

## Provenance and use

Original evaluation documents and alternate drafts are preserved outside this public repository. `provenance.json` identifies the source document, its hash and section, the original extracted-file hash, and the current file hash. Initial extraction joined Word paragraph text with LF line endings. For subsequently revised files, revision commit IDs distinguish the current query from that initial extraction; `checksums.sha256` describes the current files.

The JavaScript retains the original database-client notation db(...).collection(...); it is not presented as a tested mongosh command. Database names, example identifiers, formatting and query expressions may have been revised; consult the recorded commits. Check the intended database, generated field names, parameters and client dialect before execution.

SQL/MongoDB result normalization, comparison outputs and the final selection are not supplied by these files. Do not infer query equivalence from their presence in the package.

## Repository revisions

Q1 JavaScript now resolves independent playlists through a lookup and uses a revised user identifier; its target representation must be selected consistently with the intended model. Q2 SQL includes the corrected `SS.style_id` join and formatting changes. These revisions do not by themselves establish correspondence with the published query outputs.

# Comparing SQL and MongoDB query results

This is a proposed procedure for a **new reproduction**, based on inspection of the packaged query projections. It is not a recovered account of the exact export tools, normalization implementation or matching settings used in the original evaluation. No query has been executed or equivalence result produced by this procedure during packaging.

## Inputs and scope

Compare PostgreSQL source data with its own migrated MongoDB counterpart: Music Streaming and TPC-C at S and M, and the fixed Northwind instance. Record source/target database names, sizes, query-file hashes, actual parameter values and executable/model versions. Use the canonical embedded-playlist structure for the current Q1; the independent-playlist variant concerns adaptation.

Choose identical logical filter values on both sides. A recorded UUID can be absent from regenerated Music data; select and record a source user that exercises the query, adapting both query copies consistently. Preserve those working copies separately. Do not interpret two empty results for an absent identifier as evidence for the intended access pattern.

## Corresponding output fields

Export the complete result, not a client's truncated display. Map each SQL output and MongoDB projection to the ordered logical fields below; SQL output aliases or driver count-column labels need not match MongoDB keys literally.

| Query | Logical output fields, in order | Notes |
| --- | --- | --- |
| Q1 | user_id, playlist_id, position, song_id, title | SQL position_idx corresponds to MongoDB position |
| Q2 | title, name | Style names associated with the selected song |
| Q3 | qualifying_user_count | SQL COUNT(*) corresponds to MongoDB total_usuarios; compare the scalar value |
| Q4 | company_name, order_id, order_date | Normalize the date representation explicitly |
| Q5 | order_id, customer_id, product_name, category_name | Preserve repeated result rows |
| Q6 | employee_id, first_name, last_name | DISTINCT/grouping is already part of the queries; do not add blanket deduplication |
| Q7 | warehouse, district, customer_total, sold_items | Inspect numeric conversions in aggregate totals |
| Q8 | h_c_id, h_c_d_id, h_c_w_id, h_date, h_amount | Normalize dates and numeric amounts explicitly |
| Q9 | item_id, item_name, item_price, stock_quantity | Preserve multiplicity, including items occurring in multiple warehouses |

## Export and normalization rules

1. Keep both raw exports and metadata. Use a lossless export that preserves nulls, numeric types/precision and dates; record the client and export format. The supplied db(...).collection(...) notation is not an already tested mongosh command.
2. Align fields using the table above. Exclude only non-projected transport metadata; do not silently discard business fields, unresolved identifiers or failed conversions.
3. Keep strings exact, including case and whitespace. Do not conflate SQL NULL, an empty string, a missing field and the literal string "null". An absent projected field requires inspection and an explicitly justified mapping before it can be treated as null.
4. Normalize numeric wrappers into a documented numeric representation without losing decimal digits. Integer 10 and exact decimal 10.0 may represent the same value, but a string identifier "0010" must not be coerced into 10. Q7-Q9 contain double conversions: report precision differences rather than introducing an arbitrary tolerance or rounding to force equality. Any tolerance used in a future run must be explicit, justified and reported separately from exact equality.
5. Normalize date wrappers only after establishing the source datatype, precision and timezone semantics. Preserve date-only values as dates; do not silently assign a timezone to a timezone-free timestamp. Normalize known timezone-aware instants consistently and retain the raw values. Document unresolved date discrepancies.
6. Compare complete tuples as **multisets**: each value tuple must occur the same number of times on both sides. Physical row order and JSON object-key order are irrelevant to value equivalence. Retain duplicates. If testing an explicit sort as an additional property, report it separately and allow ties unless the query specifies a total order.
7. Handle Q3's scalar aggregate explicitly. SQL COUNT(*) returns a zero-valued row for no matches; an empty MongoDB count pipeline result may encode zero. Record this query-specific mapping if applied; it does not permit treating arbitrary empty result sets as zero-valued rows. Table 11's Q3 cardinality of one describes an aggregate output row, not the qualifying-user count.

## Comparison record

For every query/instance retain raw export filenames and hashes, field mappings, parameters, normalization decisions, normalized row counts and tuple multiplicities. Record whether exact multiset equality holds. On failure, retain missing/excess tuples with their multiplicities and classify conversion differences separately from value or query differences.

Report Table 11 values as paper observations, available in [results/query-equivalence](../../results/query-equivalence/), separately from new-run findings. Cardinalities can change with regenerated data. Equal row counts alone do not establish equality; successful execution alone does not establish it either. This procedure supplies no new equivalence indicator and does not modify the preserved queries.

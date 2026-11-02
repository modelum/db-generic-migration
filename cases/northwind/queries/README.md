# Northwind queries

SQL/MongoDB query pairs corresponding to the access patterns in Table 11.

| ID | Purpose | Files |
| --- | --- | --- |
| Q4 | Customers and order dates for a selected shipper | [Q4.sql](Q4.sql), [Q4.js](Q4.js) |
| Q5 | Customer, products and categories for a selected order | [Q5.sql](Q5.sql), [Q5.js](Q5.js) |
| Q6 | Managers of employees involved in orders from a selected customer after a given date | [Q6.sql](Q6.sql), [Q6.js](Q6.js) |

## Use

Use the actual source database and its migrated counterpart, with matching filter parameters and schema representation. The JavaScript uses `db(...).collection(...)` client notation. [Comparison instructions](../../../experiments/query-equivalence/README.md#reproduction) summarize how to compare corresponding fields and values.

Database names in the scripts must correspond to the intended instance.

The [query results](../../../results/query-equivalence/) include the Table 11 summary and output examples for Q5 and Q6.

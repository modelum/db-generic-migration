# TPC-C recovered expected and generated models

## Scope

Static inspection of the newly recovered TPC-C expected XMI and its paired generated files, contrasted with the final paper and the existing generated candidates. This is a difference inventory, not an EMF Compare report, a full equivalence algorithm, or evidence of a successful published run.

Both expected and generated models contain seven DocumentTypes, including history. Both represent order_line and new_order as embedded structures under orders, with the former multivalued and the latter single-valued. However the following differences remain:

| Topic | Recovered expected model | Recovered generated model |
| --- | --- | --- |
| district and customer ownership | warehouse.districts and district.customers are reverse multivalued references | district.d_fk_w and customer.c_fk_d retain forward references |
| Shareability | Three reverse references explicitly set targetShareability=false | No explicit targetShareability values |
| Composite identifiers | _id for ordinary entities with composite identifiers | district_id, customer_id and orders_id |
| Embedded names | order_lines and new_orders | ZIP variant uses order-line and new-order; repository candidate uses order_line and new_order |
| Numeric and temporal types | DECIMAL and DATE according to derivation notes and relational XMI | DOUBLE and STRING in corresponding recovered fields |
| Stock identifier | stock_id, STRING | stock, INTEGER |

The expected notes explicitly describe non-aggregable weak-table treatment, reversed ownership references, target shareability, plural aggregate names and the datatype assumptions. These are substantive comparison criteria, not differences that should be dismissed as arbitrary XMI identifiers.

## Relationship with existing package candidates

The source TPC-C-relational-sinPK.xmi in this ZIP matches the packaged source after line-ending normalization. The paired generated U-Schema and Document models differ from the packaged candidates in aggregate/entity spelling (hyphens versus underscores). Preserve their provenance separately. The expected model uses yet another naming convention, documented in its derivation notes.

## Historical context from recovered notes

TPC-C_XMIvsAWSschema.txt is dated 2026-07-09 and records missing foreign keys in the SQL database and discussion of tables without keys. TPC-C_Revision-USchema-Generado_MJ.txt is dated 2026-07-23 and identifies the need for upperBound=1 for new_order, whose identifying FK covers its entire PK. The recovered generated U-Schema now sets upperBound=1 for that aggregate, so this particular correction is represented in the files.

TPC-C_Revision-DocumentSchema-Generado_MJ.txt is dated 2026-07-30 and mixes model inspection with migrated-instance examples, including incorrect decimal values. It is historical evidence, not a final all-properties model comparison. SobreSchemaModelComparison.docx recommends creating missing expected models; it does not establish that the later three-case comparison was completed. These contextual files remain in the original downloaded archive rather than being presented as final experimental results.

## Required next evidence

Locate the final generated models and comparison configuration/results used for Section 6.3.1. Verify the expected model against the final paper, then run the corresponding final transformation/runtime against the exact source and compare it with the independent reference. The current recovered pair does not support a claim of zero differences. No original evidence has been overwritten or adjusted to manufacture agreement.

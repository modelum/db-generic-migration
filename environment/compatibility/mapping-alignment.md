# Mapping alignment with the final paper

## Selection policy

The final paper defines the intended behavior. The development repository and group OneDrive are complementary artifact sources; neither is authoritative for every component. Preserve recovered source files and record discrepancies before implementation changes. This review does not replace the paper with an older implementation.

The inspected development main commit is af55cd661bc69c72eb7b19471d09e58431016484. File hashes are recorded in mapping-review-provenance.json. This is a static review, not an execution or proof of the complete implementation.

## Confirmed gaps in inspected code paths

| Requirement or compatibility point | Evidence in recovered code | Next action |
| --- | --- | --- |
| Document reference target shareability | DataMigration Uschema2Document.xtend lines 198-227 creates references with name, target and type but does not set targetShareability. No such symbol was found in inspected repository source or textual members of its ZIP archives. | Locate a corrected mapping version or implement the paper rule explicitly and validate its cases. Regenerating EMF libraries alone is insufficient. |
| Reverse mapping of shareability | RoundTripV2 Document2Uschema.xtend lines 103-128 creates a reference and attribute from target and type; it does not inspect shareability or create the synthesized opposite described by the final paper. | Review Document-to-U-Schema rule DU4 against the final paper and validate the reverse mapping independently. |
| Reverse lower multiplicity | The same reverse method sets lowerBound=1 at line 119; the final paper DU4 sets zero because the Document model does not retain the direct lower bound. | Resolve the code/paper discrepancy and include a focused expected-model case. |
| Expanded Document primitive types | DataMigration Uschema2Document.xtend lines 451-467 creates only STRING, INTEGER, DOUBLE and BOOLEAN primitive instances. | Review the complete datatype correspondence before adding DECIMAL and temporal support; an expanded Ecore alone does not implement it. |
| Required Array.name in the OneDrive Ecore | createArrays at lines 471-487 creates four arrays and assigns types but no names. | Confirm the intended final metamodel constraint and corresponding mapping. Do not invent names in existing XMI files solely to satisfy validation. |
| Scalar versus array-valued data references | DataMigration DocumentAPI.java writeReferences always appends listRef after iterating references, while schema generation chooses a primitive type for upperBound=1. | Check the actual final data representation and query normalization, using recovered query evidence. Do not claim full equivalence from schema checks alone. |

The missing shareability support and type expansion require inspection of source behavior as well as generated runtime classes. No changes to the transformations or migrator have been made as part of this review.

## Preserved candidates and remaining sequence

1. Keep the OneDrive Ecores as candidate definitions consistent with the newly recovered features, pending full paper alignment.
2. Keep GitHub code as the latest available development source; its recency does not imply that each paper rule is implemented in the inspected path.
3. Locate final independently specified expected models and comparison cases in the group schema-validation folders before changing mapping behavior.
4. Select matching generated runtime packages or regenerate them from confirmed Ecores, then build the selected mappings.
5. Execute schema-level tests and regenerate models into a separate output location. Promote outputs only after comparison with independent references.
6. Verify data conversion and query equivalence separately; original historical observations and new reruns must remain distinguishable.

This sequence prepares the package without silently rewriting published evidence or presenting historical artifacts as final experimental results.

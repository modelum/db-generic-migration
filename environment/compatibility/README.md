# Recovered artifact compatibility audit

The final paper defines the intended package scope. This audit records why the recovered files cannot yet be promoted together as a verified runnable release. It does not change the paper or prove that the final experiments used these historical binaries.

## Evidence inspected on 2026-10-30

| Recovered Document model | Unnamed arrays | Primitive types with omitted datatype | Explicit targetShareability values |
| --- | --- | --- | --- |
| Music Streaming candidate | 4 | 1 | 0 |
| Northwind candidate | 4 | 1 | 0 |
| TPC-C candidate | 4 | 1 | 0 |

The newer OneDrive Document Ecore requires Array.name. It also changes the default DataType literal from BOOLEAN to STRING. An omitted datatype in old serialized XMI therefore cannot simply be interpreted using the newer definition without checking its intended meaning. Missing targetShareability values would use the newer default true; that alone does not establish preservation of inverse multiplicity.

Static inspection of generated class symbols in the two development documentMM.jar copies and the recovered AWS migrator.jar found no TargetShareability symbol in documentschema.Reference, no getName symbol in documentschema.Array, and no DECIMAL or DATETIME symbols in documentschema.DataType. These findings are consistent with the earlier Document metamodel rather than the newly recovered Ecore. The checks do not execute Java or establish behavior of the entire migrator. Hashes identify the precise files inspected in `recovered-version-audit.json`.

## Packaging decisions

- Keep recovered model files unchanged and labelled as candidates.
- Keep the newer OneDrive Ecores as current recovered candidates, with older Ecores preserved under software/metamodels/historical.
- Do not copy the inspected JARs into the package as a validated final release.
- Do not invent Array names, add shareability values, or reinterpret omitted datatype literals to make checks pass.
- Do not treat absence in recovered files as evidence that no corrected final version exists.

## Evidence needed to close the gap

Locate the final schema-transformation source and generated EMF runtime used with the newer metamodels, together with the case outputs and independently specified expected models. Check shareability mapping, datatype conversion/defaults and array representation. Then regenerate or verify the case models, run EMF validation, and compare generated outputs against the independent references.

The data migrator decimal issue seen in historical query notes is a separate execution concern. Ecore datatype additions do not by themselves prove that decimal conversion was corrected; that requires matching source/runtime and data-level validation.

Full EMF validation, compilation, schema transformation and database migration have not been run. This report is a static compatibility inventory.

## Source mapping review

[mapping-alignment.md](mapping-alignment.md) records the additional code-level comparison against the final paper. The review confirms that runtime regeneration alone will not close all gaps. No implementation changes have been made.

# Round-trip schema preservation

The experiment transforms Relational -> U-Schema -> Document -> U-Schema -> Relational for Music Streaming, Northwind and TPC-C.

## Recovered models

`recovered-models/<case>/forward/` contains the relational input and generated U-Schema/Document models for Music Streaming and Northwind. `reverse/` contains the generated U-Schema and reconstructed relational models for all three cases. The recovered TPC-C archive contains only reverse outputs; its forward inputs and outputs are not supplied by this archive. The TPC-C forward triplet is already available as [relational input](../../cases/tpcc/source/TPC-C-relational-sinPK.xmi), [U-Schema output](../../cases/tpcc/generated/TPC-C-uschema-sinPK-generated.xmi) and [Document output](../../cases/tpcc/generated/TPC-C-document-sinPK-generated.xmi). These match the files in `RoundTripV2/roundtrip/direct`; its `reverse` files match the recovered TPC-C reverse outputs after line-ending normalization. The subsequently downloaded OneDrive `schema-migration-modelos-usados/TPC-C-relational-sinPK.xmi` is byte-identical to the packaged input. These checks establish artifact correspondence, not a fresh transformation run.

These are preserved outputs from the recovered archive, not new runs of the packaged software. Duplicate copies and the Northwind `_old` variant are omitted. Archive member paths and original-byte hashes are recorded in [provenance.json](provenance.json) and [checksums.sha256](checksums.sha256).

## Comparisons used in the evaluation

The authors confirm that comparisons used **Eclipse EMF Compare with visual inspection of differences**. For the forward transformation, generated models were compared with expected models; for the round-trip, the reconstructed relational model was compared with its original relational input. No saved EMF Compare report is available. The separately developed `EMFComparator` tool was not used in the evaluation.

Recovered forward expected models are kept under each case's `expected/` directory. Their original OneDrive location under `roundtrip/Roundtrip-expected-models` does not make them expected reconstructed relational models. The initial relational model supplies the round-trip reference.

[Table 8 metrics](../../results/roundtrip/preservation-metrics.csv) are transcribed from the paper. Recovered worksheets are consistent with the reported rounded metrics; they are not machine-readable EMF Compare reports. In Music Streaming, the worksheet contains division-by-zero cells for constraint precision/F1, whereas the paper reports zero for that category. TPC-C's worksheet counts nine supported foreign keys, excluding the unsupported order_line-to-stock relationship, and treats constraints as not applicable.

## Variant selection

Music Streaming forward models here differ from `cases/music-streaming/generated/`: `is_premium` has U-Schema type `double` instead of `int`, synthetic Document key names differ, and U-Schema aggregate back-references are present. Both recovered sets retain their own provenance; no replacement or compatibility claim is made. Northwind forward models match the existing case generated models after line-ending normalization.

Only XML parsing, copy integrity and artifact inspection were performed during packaging. Runtime compatibility, transformations and EMF comparisons have not been rerun. A new reproduction should record its runtime versions, exact compared files and EMF Compare settings, and save its outputs separately.

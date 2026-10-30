# Schema validation

Recovered expected Document models are available for [Music Streaming](../../cases/music-streaming/expected/), [TPC-C](../../cases/tpcc/expected/) and [Northwind](../../cases/northwind/expected/). Music Streaming and Northwind also include expected U-Schema models. These are preserved source artifacts, not evidence of successful comparison runs.

Generated candidates remain separate in each case's generated directory. The TPC-C recovered alternative is retained separately in [recovered-tpcc-variant/](recovered-tpcc-variant/).

The paper describes incremental mapping tests and comparison of generated target Document models with expected models, complemented by manual inspection (Sections 6.2 and 6.3.1). Reproduction requires matching source/runtime versions, a documented comparison configuration and the corresponding inputs.

The authors confirm that the evaluation used Eclipse EMF Compare and visual inspection of differences, without retaining a comparison report. The separate, internally developed EMFComparator tool was not used. For round-trip reference models and recovered outputs, see [roundtrip](../roundtrip/).

Only XML parsing, artifact provenance and copy integrity have been checked during packaging. Comparison procedures, EMF validation and saved comparison outputs are not included as completed verification. No successful comparison result should be inferred from the filenames.

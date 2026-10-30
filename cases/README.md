# Case studies

| Case | Source | Generated models | Expected models | Data preparation | Queries |
| --- | --- | --- | --- | --- | --- |
| Music Streaming | [source](music-streaming/source/) | [generated](music-streaming/generated/) | [Document and U-Schema](music-streaming/expected/) | [generator/configuration](music-streaming/data/) | [Q1-Q3](music-streaming/queries/) |
| TPC-C | [source](tpcc/source/) | [generated](tpcc/generated/) | [Document and notes](tpcc/expected/) | [HammerDB settings](tpcc/data/) | [Q7-Q9](tpcc/queries/) |
| Northwind | [source](northwind/source/) | [generated](northwind/generated/) | [Document and U-Schema](northwind/expected/) | [fixed data/loading aids](northwind/data/) | [Q4-Q6](northwind/queries/) |

Source inputs, recovered generated outputs, expected models and derived preparation aids remain separately identified. Provenance does not by itself establish independent derivation of expected models, runtime compatibility or successful regeneration.

Recovered [round-trip artifacts](../experiments/roundtrip/) and [Music Service adaptation outputs](../experiments/trace-adaptation/) are organized by experiment. [Results](../results/) distinguish paper transcriptions from recovered-output extractions. Packaging checks are static; end-to-end execution has not been verified.

# Reproducing the evaluation

This guide identifies available inputs, recovered outputs and remaining execution preparation. The package has not been validated by an end-to-end run. Preserved outputs and paper-transcribed results are distinct from new reproduction results.

| Experiment | Available material | Execution status |
| --- | --- | --- |
| Schema validation | Source/generated models for three cases; expected Document models for all three and expected U-Schema for Music/Northwind; documented EMF Compare and visual inspection | Model/runtime compatibility and a reproducible comparison configuration remain to be checked |
| [Round-trip](experiments/roundtrip/) | Three cases' relational references and forward/reverse artifacts; Table 8 CSV; comparison procedure | Recovered launcher exits during a trace-persistence test; full chain has not been rerun |
| [Trace adaptation](experiments/trace-adaptation/) | Music Service implementation, sequential adapted models, generated schema and updated trace; manuscript case-study clarification | Operations were evaluated individually and sequentially; included artifacts document the sequence. No new run performed |
| [Scaling](experiments/scaling/) | Music generator and paper configuration; TPC-C HammerDB preparation settings/counts; migration source; Tables 9/10 CSV | Matching executable/dependencies and full execution recipe are not validated |
| [Flat-copy](experiments/flat-copy/) | TestSpeed source; both L baseline values; recovered Music L per-table counts/times | Complete baseline execution recipe and TPC-C original output remain to be established |
| [Query equivalence](experiments/query-equivalence/) | Candidate SQL/MongoDB pairs Q1-Q9 and Table 11 summary | Final query variants, normalization/export steps and verified comparison outputs remain to be established |

## Preparation order

1. Inspect each experiment README and its artifact provenance. Use the manuscript snapshot identified in [results provenance](results/provenance.json); see the Music Service adaptation clarification rather than repeating the manuscript's TPC-C attribution.
2. Review [software dependencies](software/source-projects/DEPENDENCIES.md) and [metamodel compatibility](software/metamodels/COMPATIBILITY.txt). Source snapshots, selected Ecores and recovered JARs are not yet a validated consistent distribution. Do not infer that replacing Ecores alone makes the software runnable.
3. Prepare datasets using each case's data README: [Music Streaming](cases/music-streaming/data/), [TPC-C](cases/tpcc/data/) and [Northwind](cases/northwind/data/). Regeneration produces comparable data, not necessarily identical original random instances.
4. Use a separate working directory and retain original models/traces. Record toolchain, exact executable and input hashes, settings and commands for any new run. Existing launchers can overwrite outputs and Music adaptation is selected by the input basename.
5. Store new outputs separately from recovered reference artifacts and [paper results](results/). Timing values are hardware-dependent observations, not exact pass/fail targets. Save comparison outputs and query exports for a new reproduction.

The round-trip README provides an artifact comparison procedure usable with the recovered models. A tested build/launch command, full runtime distribution, complete query comparison procedure, license and finalized software citation metadata remain pending. No code behavior changes or new experiments were undertaken during this packaging stage.

# Schema validation

Compare generated target schemas with the expected models for [Music Streaming](../../cases/music-streaming/expected/), [TPC-C](../../cases/tpcc/expected/) and [Northwind](../../cases/northwind/expected/). Music Streaming and Northwind also include expected U-Schema models. Inputs and generated outputs are organized in the corresponding case folders.

The evaluation used Eclipse EMF Compare and visual inspection of differences. No saved EMF Compare report is supplied. The separately developed EMFComparator tool was not used.

Load matching model/runtime versions in Eclipse, compare the intended generated/expected pair, and inspect structural differences using the mapping specifications. Record the compared filenames, metamodel versions and matching configuration for a new comparison. The [round-trip guide](../roundtrip/) covers the separate comparison of reconstructed relational schemas with their original inputs.

The TPC-C [alternative model variant](recovered-tpcc-variant/) is kept separate from the case's selected generated models. Consult its notes before choosing a comparison pair. Later Ecore revisions are described in [metamodel version notes](../../software/metamodels/COMPATIBILITY.txt); they should not be imposed on the earlier experimental models without an explicit version change.

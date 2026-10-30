# Execution environment

## Reported experimental setup

The following configuration is transcribed from Section 6.1 of the author-supplied manuscript identified in [results provenance](../results/provenance.json). It describes the reported experiments, not a newly provisioned or tested environment.

| Component | Reported configuration |
| --- | --- |
| Host | Amazon Web Services, x86_64 |
| Processor | Intel Xeon, 2.50 GHz |
| Memory / storage | 8 GB RAM / 100 GB storage |
| Database deployment | Docker containers |
| Relational database | PostgreSQL 16 |
| Document database | MongoDB 7.0 |
| Java | OpenJDK 21, no explicit maximum JVM heap setting |
| Migration insert batch | 1,000 documents |
| Indexes | Enabled throughout migration |
| Repetitions | One execution per experimental configuration |

The manuscript measures the data-migration stage using system-clock timestamps, including source access, instance transformation, relationship resolution and target writes; schema transformations and initialization are excluded. The evaluated target was empty before each execution. This describes the experimental protocol; it is not an instruction to delete an existing database.

The paper does not identify an exact AWS instance type, operating-system image, Docker image digest or CPU core count in this setup description. No values are inferred for these fields. Timings characterize the reported environment and are not exact pass/fail targets for a new machine.

## Software and data preparation

- [Dependency map](../software/source-projects/DEPENDENCIES.md): source project dependencies, recovered Java declarations, connection assumptions and launcher restrictions.
- [Metamodel compatibility](../software/metamodels/COMPATIBILITY.txt): differences between selected Ecores and preserved runtime implementations.
- [Music Streaming data](../cases/music-streaming/data/): recovered generator and paper dataset configuration. Generator population batch settings are distinct from the migration insert batch above.
- [TPC-C data](../cases/tpcc/data/): HammerDB 5.0 TPROC-C, 1/10/100 warehouses and 1/4/6 build virtual users. Virtual users populate datasets, not concurrent migration workers; no transactional benchmark workload is run.
- [Northwind data](../cases/northwind/data/): fixed recovered data and PostgreSQL 16 loading aids, inspected statically but not executed during packaging.
- [Recovered executable inventory](../software/EXECUTABLES.md): binary identity and observed entry-point differences.

The Music Service adaptation correction is documented in [trace adaptation](../experiments/trace-adaptation/). It also applies to the TPC-C attribution in Section 6.1's adaptation-configuration paragraph of the supplied manuscript.

A complete runnable container environment and tested build/launch recipe are not supplied yet. The database versions reported above must not be confused with exact dependency compatibility verified for every recovered binary. No infrastructure or database execution was performed during packaging.

# Execution environment

## Experimental configuration

The evaluation used the following environment (Section 6.1 of the paper).

| Component | Configuration |
| --- | --- |
| Host | Amazon Web Services, x86_64 |
| Processor | Intel Xeon, 2.50 GHz |
| Memory / storage | 8 GB RAM / 100 GB storage |
| Database deployment | Docker containers |
| Relational database | PostgreSQL 16 |
| Document database | MongoDB 7.0 |
| Java | OpenJDK 21, without an explicit maximum JVM heap setting |
| Migration insert batch | 1,000 documents |
| Indexes | Enabled throughout migration |
| Repetitions | One execution per experimental configuration |

## Measurement protocol

Each migration started with an empty target database. Elapsed times cover source-data access, instance transformation, reference and aggregate resolution, and document writes. Schema transformation and program initialization are excluded. Timings were measured within the migrator using system-clock timestamps.

Execution times depend on the hardware and runtime environment. Reproduction should therefore focus on the correctness of the migration results and the observed scaling behavior rather than on matching the reported times exactly.

## Preparing a reproduction

Use Java 21 or later, PostgreSQL and MongoDB with the versions above, and a dedicated working directory and target database for each run. The package provides software and setup instructions rather than a container image or automated environment installer.

- [Migration execution guide](../experiments/scaling/RUNNING.md): model selection, connection arguments, commands and outputs.
- [Software dependencies](../software/source-projects/DEPENDENCIES.md): requirements for building the implementation projects.
- [Metamodel versions](../software/metamodels/): evaluation definitions and later revisions.
- [Data preparation](DATA-PREPARATION.md): preparation and row-count checks for all three cases.

Music Streaming uses a parameterized Python generator. TPC-C uses HammerDB 5.0 TPROC-C with 1/10/100 warehouses and 1/4/6 build virtual users for S/M/L; the transactional workload is not executed. Northwind uses the fixed supplied dataset. Data-population batch sizes and build virtual users are separate from migration settings.

For the predefined Music Service adaptation, follow the [adaptation instructions](../experiments/trace-adaptation/). For the baseline and query comparisons, use their respective [experiment guides](../experiments/).

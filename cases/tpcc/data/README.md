# TPC-C data preparation with HammerDB

## Tool and configuration

The evaluation used **HammerDB 5.0**, PostgreSQL 16, and the **TPROC-C** workload derived from TPC-C. HammerDB provides schema construction and population; this package does not introduce a new TPC-C data generator.

Use the [official HammerDB 5.0 release](https://github.com/TPC-Council/HammerDB/releases/tag/v5.0) rather than silently substituting a newer tool version. The official [schema build configuration documentation](https://hammerdb.com/docs/ch09s04.html) and [schema construction documentation](https://hammerdb.com/docs/ch09s05.html) describe the configuration/build workflow; those online manual pages can track newer versions, so consult the 5.0 distribution for version-specific details.

The paper's Section 6.1 gives these settings, also recorded in hammerdb-config-paper.json:

| Instance | Warehouses | Virtual users for building/populating |
| --- | ---: | ---: |
| S | 1 | 1 |
| M | 10 | 4 |
| L | 100 | 6 |

These virtual users were used for database preparation only, not for running a transactional benchmark or measuring migration throughput.

## Preparation workflow

1. Install HammerDB 5.0 and configure its PostgreSQL client prerequisites using the official distribution instructions.
2. Use a dedicated database environment for each instance. In HammerDB select PostgreSQL and TPROC-C, and configure the intended PostgreSQL server, connection credentials and target database.
3. In the schema build options set warehouses and build virtual users using the table above. Build and populate the schema using HammerDB's provided functionality and retain the success/status output. Repeat for S, M and L with distinct target databases.
4. Do not start the TPROC-C transactional driver workload: it was not run for the paper. These experiments measure migration, not official TPC-C transaction performance.
5. Inventory the populated source tables using count-source-rows.sql and compare with the observed counts in paper-row-counts.json (Table 7). Preserve the newly observed counts and tool settings.
6. Export the schema through PostgreSQL pg_dump. The recovered [source files](../source/) contain the schema-only SQL dump and selected relational XMI; the dump does not contain the source data.
7. For the paper's schema-transformation input, the DDL was converted to relational XMI through a custom SQL parser and logical foreign keys were added to the model because the generated database lacked those constraints. Use and inspect the recovered XMI when reproducing that input; do not treat a fresh SQL parse without those additions as the same model.

This is a documented reconstruction of the setup from the paper and recovered author information, not an archived original AWS automation script. No HammerDB generation or database connection has been executed during packaging. Connection-specific commands and an end-to-end tested setup script are not supplied here.

## Source-model scope

TPC-C-relational-sinPK.xmi has nine tables, 92 columns, eight key elements and nine foreign-key elements; history has no key. The recovered expected-model notes explicitly exclude the order_line to stock foreign key because that mapping configuration does not support foreign keys targeting associative tables. Keep this input scope visible when comparing it with a benchmark specification or a fresh schema extraction. The database rows were not modified by adding relationships to the schema model.

## Randomness and comparison limits

No fixed random seed was specified for the reported HammerDB builds. Regeneration produces comparable instances but does not recover the exact original identifiers, values or timestamps. In particular, order_line contains a generated number of lines per order, so its exact count and the total row count can differ from the observed Table 7 values. Those values are reference observations, not a guarantee that an unseeded new build is byte-identical or has exactly the same order_line count.

Queries with selected customers, dates and numeric projections must therefore be evaluated against the actual generated source and its migrated counterpart. Do not reuse recorded result values as a universal oracle for every regenerated dataset.

## Included artifacts

- hammerdb-config-paper.json: documented S/M/L generation settings.
- paper-row-counts.json: observed counts in the final paper, Table 7; totals are 598,950, 5,087,358 and 49,997,496.
- count-source-rows.sql: a read-only count query prepared for this package; not an original experimental log.
- provenance.json: sources, derivation status and integrity.

These files document preparation; they are not completion evidence for schema validation, round-trip metrics or migration performance.

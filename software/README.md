# Software

| Component | Location |
| --- | --- |
| Configurable migration executable and instructions | [data-migrator](data-migrator/) |
| Schema transformations and round-trip | [schema-transformations](schema-transformations/) |
| Trace updater | [trace-updater](trace-updater/) |
| Flat-copy baseline | [flat-copy](flat-copy/) |
| Implementation projects | [source-projects](source-projects/) |
| Metamodel definitions and version notes | [metamodels](metamodels/) |

[DEPENDENCIES.md](source-projects/DEPENDENCIES.md) records project dependencies. [EXECUTABLES.md](EXECUTABLES.md) distinguishes executable versions. The configurable migrator has been tested by its developer; a clean-checkout build and execution of the complete public package have not been independently verified during preparation.

The later Ecore revisions are separate from the implementation used for the evaluation. Reproducing those experiments does not require adopting the later revisions or regenerating their models.

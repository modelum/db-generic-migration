# Recovered executable inventory

This inventory identifies JARs in the recovered `aws-instance.zip`; the current software versions are described in [Sources and versions](../SOURCES.md). The binaries have not been added to the public package or executed during packaging.

| Archive member | Observation from static inspection | Scope |
| --- | --- | --- |
| `migrator.jar` | Entry point `um.modelum.migration.Principal`; byte-identical to the earlier recovered AWS copy. Adaptation calls were not found in that entry-point class. | Recovered migration executable; no exact source-commit correspondence established |
| `migrator5000.jar` | Same entry-point name and file size as migrator.jar, different SHA-256; adaptation calls not found in its entry-point class. | Distinct variant; the filename alone does not establish its batching configuration |
| `migratoru.jar` | Entry-point class contains trace-updater calls including disaggregate, rename and removeAttribute and the MusicService trace filename. | Recovered executable containing the Music adaptation example |

Static class inspection identifies these differences; it does not establish successful execution, complete behavioral equivalence to the source snapshots, or which binary produced every reported performance measurement. No variant is designated a final supported distribution on the basis of its filename or download date.

The preserved implementation sources are under [source-projects](source-projects/). Consult [DEPENDENCIES.md](source-projects/DEPENDENCIES.md) and [metamodel compatibility](metamodels/COMPATIBILITY.txt) before assembling an executable distribution. Selecting the binary/dependency combination for each experiment remains a release-preparation task.

## Newly supplied configurable executable

The [2026-10-30 executable](data-migrator/configurable-2026-10-30/) is published separately with nine-argument usage. It was downloaded as migrator.jar.zip but is already a JAR; publication preserves the bytes under migrator.jar. It retains the automatic Music adaptation branch. It does not replace or retrospectively identify any historical measurement binary.

The executable at the existing configurable distribution path was replaced on 2 November 2026 by the supplied build excluding Oracle JDBC. The original AWS variants remain historical references; this replacement does not identify the binary used for the paper timings.

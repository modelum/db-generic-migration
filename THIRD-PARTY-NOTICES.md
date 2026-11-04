# Third-party components

The GPL-3.0 license in this repository applies to the project's own code, with the limited Eclipse permission in [LICENSE-ECLIPSE-EXCEPTION](LICENSE-ECLIPSE-EXCEPTION). Third-party code retains its original copyright notices and license terms. This includes libraries bundled in executables, generated files carrying upstream notices and third-party data. The paper and third-party datasets are not relicensed by the software LICENSE.

## Eclipse libraries

EMF, Xtext/Xtend and Eclipse Platform runtime components are used by the implementation. The supplied executable contains EPL-2.0 notices; the project permission also covers identified components supplied under EPL-1.0. The permission grants rights over the project's own code only. Retain the applicable Eclipse license/notices and satisfy source-availability obligations for the distributed components.

- EMF dependencies in the source classpath: common 2.44.0, ecore 2.41.0 and ecore.xmi 2.39.0. Embedded Maven metadata can include SNAPSHOT qualifiers.
- Xbase/Xtend runtime metadata in the supplied JAR: 2.41.0.
- Eclipse Platform components from org.eclipse.core, org.eclipse.equinox and org.eclipse.osgi are also included; their exact versions and source bundles are listed below.

Official source and license information: [EMF](https://github.com/eclipse-emf/org.eclipse.emf), [Xtext/Xtend](https://github.com/eclipse-xtext/xtext), [Eclipse Platform](https://github.com/eclipse-platform), [EPL-2.0](https://www.eclipse.org/legal/epl-2.0/).

## Other library families

| Components | License family / distribution information |
| --- | --- |
| PostgreSQL JDBC (source classpath: 42.7.10) | [BSD-2-Clause](https://jdbc.postgresql.org/license/); retain its copyright/license |
| MongoDB BSON and Java drivers (source classpath: 5.1.2) | Apache-2.0; see [upstream](https://github.com/mongodb/mongo-java-driver) |
| Netty buffer/handler/transport (embedded metadata: 4.1.87.Final) | Apache-2.0 and applicable bundled third-party notices; see [upstream](https://github.com/netty/netty) |
| Guava (embedded metadata: 33.5.0-jre) | Apache-2.0 and applicable notices; see [upstream](https://github.com/google/guava) |
| SLF4J API (embedded metadata: 1.7.6) | MIT; see [upstream license](https://www.slf4j.org/license.html) |
| OSGi annotation/service metadata components | Embedded Maven license declarations specify Apache-2.0; retain supplied notices |
| OnGres SCRAM/stringprep components | BSD-2-Clause notices are included under META-INF/licenses in the executable |

Applicable full texts and notices accompany this repository under [LICENSES](LICENSES/): [Apache-2.0](LICENSES/Apache-2.0.txt), [EPL-2.0](LICENSES/EPL-2.0.txt), [SLF4J MIT](LICENSES/SLF4J-1.7.6-MIT.txt), [PostgreSQL JDBC](LICENSES/PostgreSQL-JDBC-LICENSE.txt), [OnGres](LICENSES/OnGres-LICENSES.txt), [Netty notices](LICENSES/Netty-4.1.87-NOTICE.txt), [MongoDB notices](LICENSES/MongoDB-5.1.2-NOTICES.txt) and [OSGi notices](LICENSES/OSGi-NOTICE.txt).

The Apache text applies to the Apache-licensed components listed above, including MongoDB, Guava and OSGi. Version-specific MongoDB and Netty upstream notices are retained; upstream notice collections can describe additional components beyond the subset present in this executable. The EPL text is reproduced from the SPDX license-text collection. PostgreSQL/OnGres and OSGi notices are preserved from their original dependency/distribution resources.

## Eclipse source code

[Download the Eclipse dependency sources](software/third-party-sources/eclipse-sources.zip). The archive contains fifteen unmodified official source bundles, their download index and the EPL-2.0 text. Their original copyright and license notices are retained.

The corresponding official binary bundles were identified by byte-for-byte comparison of class files with the supplied migrator. The selected bundles account for all `org/eclipse/` classes in that executable. Each source bundle's `Eclipse-SourceBundle` manifest header was checked against the binary component ID and full version. This checks dependency correspondence; it is not an independent rebuild of the application.

| Component | Version | Official source bundle |
| --- | --- | --- |
| `org.eclipse.core.contenttype` | `3.9.800.v20251105-1620` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.core.contenttype.source_3.9.800.v20251105-1620.jar) |
| `org.eclipse.core.jobs` | `3.15.700.v20250725-1147` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.core.jobs.source_3.15.700.v20250725-1147.jar) |
| `org.eclipse.core.runtime` | `3.34.100.v20251111-1421` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.core.runtime.source_3.34.100.v20251111-1421.jar) |
| `org.eclipse.emf.common` | `2.44.0.v20251025-0946` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.emf.common.source_2.44.0.v20251025-0946.jar) |
| `org.eclipse.emf.ecore` | `2.41.0.v20251025-0946` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.emf.ecore.source_2.41.0.v20251025-0946.jar) |
| `org.eclipse.emf.ecore.xmi` | `2.39.0.v20250910-1205` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.emf.ecore.xmi.source_2.39.0.v20250910-1205.jar) |
| `org.eclipse.equinox.app` | `1.7.500.v20250629-0337` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.equinox.app.source_1.7.500.v20250629-0337.jar) |
| `org.eclipse.equinox.common` | `3.20.300.v20251111-0312` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.equinox.common.source_3.20.300.v20251111-0312.jar) |
| `org.eclipse.equinox.preferences` | `3.12.100.v20251111-0704` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.equinox.preferences.source_3.12.100.v20251111-0704.jar) |
| `org.eclipse.equinox.registry` | `3.12.600.v20250906-0651` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.equinox.registry.source_3.12.600.v20250906-0651.jar) |
| `org.eclipse.osgi` | `3.24.0.v20251126-0427` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.osgi.source_3.24.0.v20251126-0427.jar) |
| `org.eclipse.osgi.compatibility.state` | `1.3.0.v20251022-1724` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.osgi.compatibility.state.source_1.3.0.v20251022-1724.jar) |
| `org.eclipse.xtend.lib` | `2.41.0.v20251124-0739` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.xtend.lib.source_2.41.0.v20251124-0739.jar) |
| `org.eclipse.xtend.lib.macro` | `2.41.0.v20251124-0739` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.xtend.lib.macro.source_2.41.0.v20251124-0739.jar) |
| `org.eclipse.xtext.xbase.lib` | `2.41.0.v20251124-0739` | [Download](https://download.eclipse.org/releases/2025-12/202512101000/plugins/org.eclipse.xtext.xbase.lib.source_2.41.0.v20251124-0739.jar) |

The same source bundles are supplied in the local archive so obtaining the corresponding Eclipse sources does not depend solely on the external links. Preserve their notices and the applicable source availability when redistributing the libraries; the project linking permission does not waive the Eclipse license requirements.

## Data and other materials

Northwind and any other third-party materials retain their applicable upstream terms; see the [case studies](cases/) for the datasets. Do not infer a new dataset license from the software license. Project funding acknowledgment and paper citation remain in the main README.

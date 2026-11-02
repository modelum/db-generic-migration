# Third-party components

The GPL-3.0 license in this repository applies to the project's own code, with the limited Eclipse permission in [LICENSE-ECLIPSE-EXCEPTION](LICENSE-ECLIPSE-EXCEPTION). Third-party code retains its original copyright notices and license terms. This includes libraries bundled in executables, generated files carrying upstream notices and third-party data. The paper and third-party datasets are not relicensed by the software LICENSE.

## Eclipse libraries

EMF, Xtext/Xtend and Eclipse Platform runtime components are used by the implementation. The supplied executable contains EPL-2.0 notices; the project permission also covers identified components supplied under EPL-1.0. The permission grants rights over the project's own code only. Retain the applicable Eclipse license/notices and satisfy source-availability obligations for the distributed components.

- EMF dependencies in the source classpath: common 2.44.0, ecore 2.41.0 and ecore.xmi 2.39.0. Embedded Maven metadata can include SNAPSHOT qualifiers.
- Xbase/Xtend runtime metadata in the supplied JAR: 2.41.0.
- Eclipse Platform classes from org.eclipse.core, org.eclipse.equinox and org.eclipse.osgi are also present. A complete original-library/version inventory is needed when repackaging the executable.

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

This table identifies component families and does not replace their full license texts. Retain full applicable license/NOTICE files with a repackaged distribution and provide corresponding source where required. Do not assume one merged META-INF/LICENSE or NOTICE file covers every bundled library.

## Current executable: Oracle removal pending

The executable under `software/data-migrator/configurable-2026-10-30/` still includes Oracle JDBC classes and an Oracle Free Distribution, Hosting, and Use Terms and Conditions notice. **That component is not covered by GPL-3.0 or the Eclipse exception.** A replacement JAR without Oracle is being prepared. Until it is replaced, the existing executable must not be described as a fully GPL-covered distribution. Neither this notice nor the new project license establishes compatibility of the existing Oracle-containing combined executable.

## Data and other materials

Northwind and any other third-party materials retain their applicable upstream terms; see [SOURCES.md](SOURCES.md) for their origins. Do not infer a new dataset license from the software license. Project funding acknowledgment and paper citation remain in the main README.

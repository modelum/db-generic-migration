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

Applicable full texts and notices accompany this repository under [LICENSES](LICENSES/): [Apache-2.0](LICENSES/Apache-2.0.txt), [EPL-2.0](LICENSES/EPL-2.0.txt), [SLF4J MIT](LICENSES/SLF4J-1.7.6-MIT.txt), [PostgreSQL JDBC](LICENSES/PostgreSQL-JDBC-LICENSE.txt), [OnGres](LICENSES/OnGres-LICENSES.txt), [Netty notices](LICENSES/Netty-4.1.87-NOTICE.txt), [MongoDB notices](LICENSES/MongoDB-5.1.2-NOTICES.txt) and [OSGi notices](LICENSES/OSGi-NOTICE.txt).

The Apache text applies to the Apache-licensed components listed above, including MongoDB, Guava and OSGi. Version-specific MongoDB and Netty upstream notices are retained; upstream notice collections can describe additional components beyond the subset present in this executable. The EPL text is reproduced from the SPDX license-text collection. PostgreSQL/OnGres and OSGi notices are preserved from their original dependency/distribution resources.

These notices do not replace obligations to supply or make available corresponding library sources where required. Preserve applicable notices when repackaging and use the source repositories and component versions identified above. An inventory of full version-matched Eclipse Platform source artifacts is not included; the exception over project code does not waive Eclipse source-availability requirements.

## Data and other materials

Northwind and any other third-party materials retain their applicable upstream terms; see [SOURCES.md](SOURCES.md) for their origins. Do not infer a new dataset license from the software license. Project funding acknowledgment and paper citation remain in the main README.

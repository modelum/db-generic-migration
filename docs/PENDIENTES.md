# Estado y cuestiones pendientes del replication package

Estado a 30 de octubre de 2026. El objetivo es publicar ahora una base organizada y transparente, y cerrar después los puntos necesarios para reproducir la evaluación. El paquete todavía no es una reproducción validada de extremo a extremo. El paper define el alcance; GitHub y OneDrive aportan artefactos que deben seleccionarse por su correspondencia y compatibilidad.

## Qué publicamos ahora

| Estado | Contenido |
| --- | --- |
| Incluido | Esquemas fuente de los tres casos, con procedencia y hashes. |
| Incluido | Datos Northwind y carga preparada para PostgreSQL 16, revisada solo estáticamente. |
| Candidato | U-Schema y Document de los tres casos, con limitaciones explícitas. |
| Candidato | Expected Document TPC-C y notas; la pareja recuperada presenta diferencias. |
| Candidato | Ecore de OneDrive, con versiones previas separadas como históricas. |
| Incluido | Estructura de experimentos e informes de compatibilidad. |
| Fuera del paquete | Código ejecutable final, generadores, consultas consolidadas y registros finales; parte está localizada pero no incorporada ni validada. |

## Criterio para interpretar los pendientes

Alta: necesaria para la correspondencia entre paper y artefactos o para la validez de las comprobaciones. Media: necesaria para reproducir, documentar o distribuir el paquete completo. Baja: mejora organizativa. Una discrepancia en archivos recuperados no demuestra que la ejecución final tuviera el mismo problema. No localizado no significa inexistente. Los registros originales no deben sustituirse silenciosamente por nuevas ejecuciones.

## Compatibilidad y modelos

### P01 Fijar el conjunto de software y metamodelos

Prioridad: Alta.

**Situación:** Tenemos código de GitHub en af55cd661bc69c72eb7b19471d09e58431016484 y Ecore de OneDrive con propiedades adicionales. Los JAR inspeccionados corresponden a definiciones anteriores.

**Cierre:** Identificar las fuentes, Ecore y bibliotecas que deben compilarse juntos. Registrar commit, dependencias y hashes; obtener una compilación y una ejecución de ejemplo verificadas.

### P02 Alinear las reglas con el paper

Prioridad: Alta.

**Situación:** En las rutas revisadas no se escribe ni lee targetShareability; la transformación inversa fija lowerBound=1 donde el paper indica 0. La creación de tipos Document solo prepara cuatro tipos y no asigna nombres a los arrays.

**Cierre:** Localizar una versión corregida o acordar y validar las correcciones necesarias. Revisar también referencias escalares frente a arrays. No basta con reemplazar el Ecore o regenerar los JAR.

### P03 Cerrar los modelos esperados de los tres casos

Prioridad: Alta.

**Situación:** Se ha recuperado un expected XMI de TPC-C con notas de derivación. No coincide con los generados recuperados en referencias, identificadores, nombres y tipos. Music Streaming y Northwind aún no tienen expected XMI localizado en el paquete.

**Cierre:** Recuperar y revisar los tres modelos independientes contra las reglas finales. Ejecutar EMF Compare con configuración documentada y conservar sus resultados. No modificar el esperado solo para eliminar diferencias.

### P04 Seleccionar y validar los modelos generados

Prioridad: Alta.

**Situación:** Hay candidatos de los tres casos. Music Streaming procede de RoundTrip porque RoundTripV2 deja playlist independiente. Las variantes TPC-C difieren además en guiones y subrayados. El Ecore nuevo cambia valores por defecto y exige Array.name.

**Cierre:** Regenerar con la versión acordada, ejecutar validación EMF y comparar contra los esperados. Mantener separadas las salidas canónicas, adaptadas, históricas y nuevas.

## Datos y consultas

### P05 Recuperar el generador definitivo de Music Streaming

Prioridad: Alta.

**Situación:** El generador del repositorio no produce los tamaños de la tabla 6. Una exportación SQL recuperada sí tiene los ocho recuentos de S publicados. La semilla Python no controla por sí sola los UUID aleatorios.

**Cierre:** Localizar el generador y los parámetros definitivos S/M/L. Documentar dependencias, semilla y límites de reproducción exacta; verificar todos los recuentos y conservar los datos originales si existen.

### P06 Hacer reproducible la preparación TPC-C

Prioridad: Alta.

**Situación:** Tenemos DDL y XMI. La documentación recuperada indica HammerDB 5.0 TPROC-C, 1/10/100 almacenes y 1/4/6 usuarios virtuales, sin semilla fija. Las FK se añadieron al XMI. Las notas excluyen order_line hacia stock.

**Cierre:** Documentar o recuperar los scripts HammerDB, el parser SQL y las ediciones del XMI, incluida la FK omitida. Verificar los recuentos S/M/L. Distinguir instancias comparables de los datos exactos usados en el paper.

### P07 Validar carga y procedencia Northwind

Prioridad: Media.

**Situación:** Esquema, datos e instrucciones están en el paquete. La revisión estática cuenta 3.362 filas en 14 tablas, dos vacías. Las copias para PostgreSQL 16 omiten solo default_with_oids. La atribución recuperada y la del paper nombran fuentes diferentes.

**Cierre:** Confirmar procedencia y condiciones de redistribución. Ejecutar la carga en una base dedicada con PostgreSQL 16, conservar el resultado de las comprobaciones y revisar correspondencia DDL/XMI.

### P08 Consolidar Q1 a Q9 y la conversión de valores

Prioridad: Alta.

**Situación:** Tenemos consultas y resultados en documentos y archivos recuperados, aún fuera del paquete. Q3 usa =10 en una versión y el paper dice al menos diez. Q2 tiene un alias SQL incorrecto. Hay notas antiguas con decimales erróneos y un Word posterior con valores correctos.

**Cierre:** Elegir las consultas finales y vincularlas con la tabla 11. Verificar la conversión decimal con el ejecutable correcto. Exportar y comparar resultados de S/M y Northwind, normalizando escalares y fechas, conservando duplicados. Registrar parámetros y diferencias.

## Experimentos y resultados

### P09 Reconstruir la evidencia del round trip

Prioridad: Alta.

**Situación:** Existen entradas y modelos reconstruidos en el repositorio de desarrollo. El lanzador RoundTripV2 termina antes de completar la cadena. No hemos recuperado el matching y los recuentos que sustentan precisión, recall y F1 de la tabla 8.

**Cierre:** Preparar una ejecución completa de los tres casos y documentar correspondencias, elementos adicionales y ausentes por categoría. Recuperar la evidencia original; etiquetar cualquier nueva ejecución como tal.

### P10 Recuperar la adaptación de trazas sobre TPC-C S

Prioridad: Alta.

**Situación:** Hay implementación de operaciones y ejemplos sobre MusicService que encadenan cambios. El paper evalúa las operaciones por separado sobre TPC-C S.

**Cierre:** Obtener los casos separados de renombrado, borrado de atributo y Aggregate a Reference; conservar modelos y trazas antes y después y la generación Document posterior.

### P11 Cerrar las mediciones de migración y almacenamiento

Prioridad: Media.

**Situación:** Los valores finales están en el paper. Faltan registros originales completos por caso y tamaño que vinculen tiempo total, tiempos por colección, tamaños, entradas y ejecutable. Las guías antiguas contienen configuraciones propuestas que no se siguieron necesariamente.

**Cierre:** Recuperar registros de Music Streaming y TPC-C S/M/L y documentar entorno, índices, lote 1.000 y límites del temporizador. Respetar el protocolo final de una observación por configuración; no inventar medias o repeticiones.

### P12 Cerrar los dos baselines publicados

Prioridad: Media.

**Situación:** Tenemos código y JAR de copia plana y un lanzador Music Streaming L; el otro run_test recuperado también es Music Streaming. No hemos localizado el lanzador TPC-C L ni los registros originales de ambos baselines.

**Cierre:** Conservar la configuración equivalente de acceso, conversión y escritura con lote 1.000. Documentar los 370 s de Music Streaming L y 774 s de TPC-C L con sus evidencias. No añadir seis baselines ni pruebas de lotes solo porque los proponga una guía histórica.

## Publicación y cierre

### P13 Incorporar pruebas incrementales y compilación

Prioridad: Media.

**Situación:** El repositorio de desarrollo incluye entradas por regla y salidas generadas. Los lanzadores revisados generan archivos, pero no demuestran por sí solos comparación automática con referencias independientes.

**Cierre:** Seleccionar fuentes y pruebas de las cuatro transformaciones. Especificar versiones de Java, EMF y Xtend y dependencias; preparar comandos de compilación y comprobación con fallos explícitos.

### P14 Completar metadatos y permisos de distribución

Prioridad: Media.

**Situación:** La estructura y procedencia parcial están preparadas. No se ha elegido licencia ni completado una cita definitiva. Hay componentes y datasets externos cuya atribución debe confirmarse.

**Cierre:** Acordar licencia con los autores, completar CITATION.cff sin inventar DOI o datos bibliográficos y registrar atribuciones. Decidir alojamiento externo de datos grandes y publicar una versión estable identificable.

### P15 Verificar el paquete desde una copia limpia

Prioridad: Media.

**Situación:** Solo se han realizado comprobaciones estáticas de XML, hashes, contenido y estructura. No se ha validado el conjunto con compilación, EMF, PostgreSQL, MongoDB ni una migración completa.

**Cierre:** Ejecutar las instrucciones desde un entorno limpio, registrar límites y resultados y actualizar la matriz de estado. Mantener salidas nuevas fuera de las referencias publicadas. Una segunda persona debería poder seguir la guía sin conocimiento previo.

### P16 Separar archivo histórico y paquete final

Prioridad: Baja.

**Situación:** Se preservan candidatos e históricos para no perder procedencia. Todavía hay documentación de estructura pendiente y artefactos recuperados fuera del repositorio público.

**Cierre:** Usar un inventario único para marcar recuperado, comprobado estáticamente y reproducido. Reducir duplicados en la versión final, conservando el historial Git. No confundir planes de trabajo con resultados ejecutados.

## Orden de trabajo recomendado

1. Revisar con los autores P01–P04 y localizar versiones finales antes de corregir código.
2. Completar preparación de datos y Q1–Q9 en P05–P08.
3. Recuperar y verificar la evidencia experimental P09–P12.
4. Completar compilación, metadatos y prueba limpia P13–P16.

## Evidencias y límites

Referencia de alcance: Database_Migration_SoSyM (1)(1).pdf, secciones 6.1–6.5 y tablas 6–11. Código inspeccionado: modelum/db-generic-migration-dev, commit af55cd661bc69c72eb7b19471d09e58431016484. Las procedencias concretas y hashes están en los archivos provenance.json y checksums.sha256 del paquete. Los documentos de planificación y las conversaciones son contexto, no sustitutos de la evidencia ejecutada.

Informes de detalle: [compatibilidad](../environment/compatibility/README.md), [reglas y código](../environment/compatibility/mapping-alignment.md) y [comparación TPC-C](../experiments/schema-validation/tpcc-recovered-comparison.md).

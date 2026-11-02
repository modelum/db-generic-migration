# Sources and versions

## Implementation

Implementation projects originate from `modelum/db-generic-migration-dev`:

- DataMigration: revision `81179f6325dbdaea6c31205405939366b71bb824`, with nine connection/model arguments.
- RoundTripV2, Trace, OrionTraceChanger, TestSpeed and the three metamodel runtime projects: revision `3d422b9df84dd66332d7bb855c717b5c3ace279b`.
- The configurable `migrator.jar` was supplied on 30 October 2026. It is a later parameterized executable, not an identification of the binary used for every historical timing. Its associated source update is the DataMigration revision above; independent source-to-binary rebuild equivalence has not been checked.

Third-party libraries retain their own licenses. The repository's software license does not replace their terms.

## Data and models

Music Streaming uses the project-specific Python generator. The original generator is preserved alongside a derivative changing only DATASET_CONFIG to Table 6 parameters; the exact AWS configuration script is not archived. The model inputs, transformation outputs and expected models come from the project's implementation and evaluation materials.

TPC-C datasets were generated with HammerDB 5.0 TPROC-C. The relational model adds logical foreign keys after SQL extraction and omits the unsupported order_line-to-stock relationship. See [data preparation](cases/tpcc/data/) for the experimental settings.

Northwind is the PostgreSQL sample cited by the paper at [pthom/northwind_psql](https://github.com/pthom/northwind_psql). The precise upstream revision of the supplied SQL has not been established. Original SQL is preserved alongside PostgreSQL 16 loading aids that remove only the obsolete default_with_oids setting; retain applicable third-party terms.

The later Ecore definitions are distinct from the versions used in the evaluation; see [COMPATIBILITY.txt](software/metamodels/COMPATIBILITY.txt).

## Queries and results

Q1-Q9 originated in the evaluation documents and include subsequent corrections recorded in Git history. Current Q1 uses embedded playlists; the independent-playlist query concerns adaptation.

The CSV result summaries transcribe Tables 8-11 and the flat-copy discussion of the author-supplied manuscript `Database_Migration_SoSyM (1)(1).pdf`. They are reported results, not outputs from new executions during package preparation. The Music L baseline per-table breakdown is extracted from the existing `baseline para Music-Service-L.pdf` timing record.

The [arXiv preprint](https://arxiv.org/abs/2604.22415) may differ between revisions. The adaptation case-study attribution correction is explained in [trace adaptation](experiments/trace-adaptation/). Detailed collection inventories are maintained internally; file revisions remain available in Git history.

The original query comparison material includes SQL/JavaScript scripts with output examples and `data-migration-query-comparison-TPCC-mjose.docx`. The latter documents SQL/MongoDB results for Q7-Q9 after the Decimal128 issue was resolved; it reports ten results for Q7/Q8 and 5,486 for Q9, with selected Q9 rows. Some other large query results were retained only in part. These are original evaluation records, not full export datasets or new query executions.

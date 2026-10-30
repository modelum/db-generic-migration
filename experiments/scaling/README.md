# Data migration scaling

Music Streaming and TPC-C are evaluated at S, M and L under the manuscript's Section 6.1 configuration. [Music preparation](../../cases/music-streaming/data/) includes the recovered generator and a paper-configuration derivative. [TPC-C preparation](../../cases/tpcc/data/) records HammerDB settings and reported counts. [DataMigration](../../software/source-projects/DataMigration/) contains the migration implementation.

Reported sizes, elapsed times and throughputs are available in [results/scaling](../../results/scaling/) with manuscript provenance. These CSVs are paper transcriptions, not results of a new run of the packaged sources. Northwind has a fixed small instance, not this S/M/L scaling series.

See [RUNNING.md](RUNNING.md) for the configurable executable, canonical/adapted Music input selection, connection arguments and outputs. The supplied executable has been tested by its developer; no new performance measurement is claimed by this guide.

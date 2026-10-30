# Flat-copy baseline

The manuscript reports L-instance flat-copy baselines of 370 seconds for Music Streaming and 774 seconds for TPC-C, using batches of 1,000. [TestSpeed](../../software/source-projects/TestSpeed/) contains the single-table baseline implementation, with the local connection assumptions described in [dependencies](../../software/source-projects/DEPENDENCIES.md).

[results/flat-copy](../../results/flat-copy/) includes both reported baseline values and the recovered Music L per-table counts/times. The recovered Music displayed times sum to 370 seconds; these are rounded per-table measurements, not evidence of a single global timer. Provenance is recorded in [results](../../results/).

A complete tested sequence covering all tables, selection of the matching executable and the original TPC-C baseline output remain to be established. No new baseline was executed during packaging.

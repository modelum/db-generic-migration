# Music Streaming generated schemas

This folder contains the U-Schema and Document outputs for the relational-to-document schema chain. The relational input is in [source](../source/) and comparison models are in [expected](../expected/).

The canonical Document schema embeds `playlist` and `most_recent_song` in `app_user`, with `playlist_song` nested in `playlist`; `is_premium` is retained. The adapted Music Service scenario is documented separately under [trace adaptation](../../../experiments/trace-adaptation/).

File versions are identified in [generated-provenance.json](../../generated-provenance.json). These files are preserved experiment artifacts; they were not regenerated during package preparation. See [metamodel version notes](../../../software/metamodels/) before loading them with later Ecore definitions.

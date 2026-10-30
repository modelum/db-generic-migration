# music-streaming generated models

## Status

Recovered generated candidates for the canonical schema chain. These are not independently specified expected models, not outputs of a new run, and not yet validated final references. Original filenames and bytes are preserved.

## Selection and limitations

Selected from RoundTrip rather than RoundTripV2: the selected Document model contains five document types and embeds playlist and most_recent_song in app_user, with playlist_song nested within playlist. app_user retains is_premium. These patterns agree with Section 6.3.2 of the paper. The RoundTripV2 Document output instead contains an independent playlist document and does not embed playlist in app_user; it has not been selected. The recovered AWS outputs renamed to user_data are adapted models and have not been selected either.

This selection establishes agreement with the inspected structural patterns, not a full validation against the paper or proof that this exact file produced the final data-migration results. In particular, field naming, types, multiplicities and shareability still require comparison against independently specified expected models.

## Provenance

Copied from `db-generic-migration-dev/RoundTrip/roundtrip/direct`. The cross-case inventory is [generated-provenance.json](../../generated-provenance.json). The selected relational source is in the sibling `source/` directory. No transformation was executed during packaging.

## Metamodel alignment pending

The newer OneDrive Document Ecore changes datatype defaults and requires Array.name. These recovered generated models predate confirmation of that metamodel version; do not assume conformance or unchanged interpretation. Validate or regenerate them with the matching final software before promoting them to final references. See [metamodel notes](../../../software/metamodels/README.md).

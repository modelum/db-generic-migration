# music-streaming source schemas

The relational XMI contains eight tables and matches the MusicService input in the development repository (RoundTripV2/roundtrip/direct), ignoring line-ending differences. It is the selected source schema candidate for the paper case.

The SQL file is preserved unchanged from the recovered AWS material. It contains eight table definitions and foreign-key indexes, but its initial CREATE DATABASE musicdb-M and USE DATABASE musicdb-M statements are not a ready-to-run PostgreSQL setup. Do not execute it as a reproduction script. A tested PostgreSQL loading procedure and XMI/DDL constraint and datatype correspondence remain pending. The tiny demonstration dataset and adapted U-Schema/Document outputs are not included here.

## Provenance

Files were copied byte-for-byte from recovered group OneDrive AWS material. See checksums.sha256 for integrity. Selection is based on the inspected repository and the final paper; filenames and folder dates alone do not establish the exact experimental software version. No database or migration has been executed during packaging.

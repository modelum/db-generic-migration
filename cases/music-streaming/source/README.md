# music-streaming source schemas

The relational XMI contains eight tables and matches the MusicService input in the development repository (RoundTripV2/roundtrip/direct), ignoring line-ending differences. It is the source schema for the paper case.

The SQL file is preserved unchanged from the supplied AWS material. It contains eight table definitions and foreign-key indexes, but its initial CREATE DATABASE musicdb-M and USE DATABASE musicdb-M statements are not a ready-to-run PostgreSQL setup. Do not execute it as a reproduction script. A tested PostgreSQL loading procedure and XMI/DDL constraint and datatype correspondence remain pending. The tiny demonstration dataset and adapted U-Schema/Document outputs are not included here.


File origins and versions are documented in the accompanying provenance metadata. Original SQL and XMI files are preserved.

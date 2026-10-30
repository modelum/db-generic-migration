# TPC-C expected Document model candidate

The XMI and its derivation notes are unchanged files recovered from the group OneDrive schema comparison folder. The notes explicitly state that the expected model was derived from mapping specifications rather than copied from the generated model. This documents the claimed derivation; independent review and final paper correspondence remain pending.

The expected source is TPC-C-relational-sinPK.xmi, whose recovered ZIP copy matches the selected source after line-ending normalization. The notes explicitly omit order_line to stock because FKs targeting associative tables are not supported by the current mapping. The expected model contains seven document types, DECIMAL and temporal types, and false targetShareability on the reversed warehouse.districts, district.customers and customer.orders references.

This candidate is not equal to the recovered generated Document models. Do not edit the expected file solely to remove differences. Neither a successful EMF Compare run nor agreement with the final published comparison has been established during packaging.

File hashes and archive entries are recorded in provenance.json and checksums.sha256. Original bytes are preserved; no model transformation or validation engine has been run.

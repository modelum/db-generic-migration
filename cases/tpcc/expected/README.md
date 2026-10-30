# TPC-C expected Document model

The XMI and accompanying notes describe a Document schema derived from the mapping specifications. Its relational input is [TPC-C-relational-sinPK.xmi](../source/TPC-C-relational-sinPK.xmi). The notes exclude the order_line-to-stock foreign key because the evaluated mapping does not support foreign keys targeting associative tables.

The expected model contains seven document types, DECIMAL/temporal types and explicit false targetShareability values on the reversed warehouse.districts, district.customers and customer.orders references. These details differ from the generated implementation artifacts and must be interpreted with the [metamodel version notes](../../../software/metamodels/) rather than hidden by editing the expected file.

Use [schema-validation instructions](../../../experiments/schema-validation/) to inspect differences. File and note origins are recorded in `provenance.json`. No new EMF comparison is supplied with this package.

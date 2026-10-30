-- Expected counts derived statically from the recovered INSERT statements.
-- Two schema tables have no INSERT statements and are expected to be empty.
DO $$
DECLARE
  entry record;
  actual bigint;
BEGIN
  FOR entry IN SELECT * FROM (VALUES
    ('categories', 8),
    ('customer_demographics', 0),
    ('customers', 91),
    ('customer_customer_demo', 0),
    ('employees', 9),
    ('suppliers', 29),
    ('products', 77),
    ('region', 4),
    ('shippers', 6),
    ('orders', 830),
    ('territories', 53),
    ('employee_territories', 49),
    ('order_details', 2155),
    ('us_states', 51)
  ) AS counts(table_name, expected)
  LOOP
    EXECUTE format('SELECT count(*) FROM public.%I', entry.table_name) INTO actual;
    IF actual <> entry.expected THEN
      RAISE EXCEPTION 'Count mismatch for %: expected %, got %', entry.table_name, entry.expected, actual;
    END IF;
    RAISE NOTICE '%: % rows OK', entry.table_name, actual;
  END LOOP;
END $$;

-- Read-only inventory for a prepared TPC-C source database.
-- Run against the intended S, M or L instance and compare with Table 7.
SELECT 'customer' AS table_name, COUNT(*) AS source_rows FROM public.customer
UNION ALL SELECT 'district', COUNT(*) FROM public.district
UNION ALL SELECT 'history', COUNT(*) FROM public.history
UNION ALL SELECT 'item', COUNT(*) FROM public.item
UNION ALL SELECT 'new_order', COUNT(*) FROM public.new_order
UNION ALL SELECT 'order_line', COUNT(*) FROM public.order_line
UNION ALL SELECT 'orders', COUNT(*) FROM public.orders
UNION ALL SELECT 'stock', COUNT(*) FROM public.stock
UNION ALL SELECT 'warehouse', COUNT(*) FROM public.warehouse
ORDER BY table_name;

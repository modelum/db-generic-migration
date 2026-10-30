SELECT 
    w.w_name AS warehouse, 
    d.d_name AS district, 
    ca.customer_total, 
    oa.sold_items
FROM warehouse w
JOIN district d ON w.w_id = d.d_w_id
JOIN (
    -- Inline view 1: Pre-agregación de clientes
    SELECT 
        c_w_id, 
        c_d_id, 
        SUM(c_balance) AS customer_total
    FROM customer
    GROUP BY c_w_id, c_d_id
) ca ON ca.c_w_id = d.d_w_id AND ca.c_d_id = d.d_id
JOIN (
    -- Inline view 2: Pre-agregación de pedidos
    SELECT 
        o_w_id, 
        o_d_id, 
        SUM(o_ol_cnt) AS sold_items
    FROM orders
    GROUP BY o_w_id, o_d_id
) oa ON oa.o_w_id = d.d_w_id AND oa.o_d_id = d.d_id
ORDER BY sold_items DESC;

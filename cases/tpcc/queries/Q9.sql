SELECT s_i_id AS item_id,
       i_name AS item_name,
       i_price AS item_price,
       s_quantity AS stock_quantity
FROM stock 
     JOIN item       ON i_id = s_i_id
     JOIN warehouse  ON s_w_id = w_id
WHERE s_quantity < 15
ORDER BY s_i_id;

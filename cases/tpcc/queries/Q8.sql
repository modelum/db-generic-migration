SELECT h_c_id, h_c_d_id, h_c_w_id, h_date, h_amount
FROM history
     JOIN customer ON h_c_w_id=c_w_id AND h_c_d_id=c_d_id AND h_c_id=c_id
     JOIN district ON h_w_id=d_w_id AND h_d_id=d_id
WHERE c_w_id= 1 AND c_d_id= 2 AND c_id <= 10
ORDER BY h_c_id;

SELECT C.company_name, O.order_id, O.order_date
FROM customers C
     JOIN orders O ON C.customer_id = O.customer_id
     JOIN shippers S ON O.ship_via = S.shipper_id
WHERE S.company_name = 'Speedy Express'
ORDER BY C.company_name, O.order_date;

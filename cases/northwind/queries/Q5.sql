SELECT D.order_id, O.customer_id, P.product_name, C.category_name
FROM order_details D 
     JOIN orders O ON D.order_id = O.order_id
     JOIN products P ON D.product_id = P.product_id
     JOIN categories C ON P.category_id = C.category_id
WHERE D.order_id = 10248;

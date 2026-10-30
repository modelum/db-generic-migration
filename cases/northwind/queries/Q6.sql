SELECT DISTINCT M.employee_id, M.first_name, M.last_name
FROM customers C
       JOIN orders O ON C.customer_id = O.customer_id
       JOIN employees E ON O.employee_id = E.employee_id
       JOIN employees M ON E.reports_to = M.employee_id
WHERE C.customer_id = 'ALFKI'
  AND O.order_date > '1997-08-29';

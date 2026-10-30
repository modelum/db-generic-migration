db('Northwind').collection('customers').aggregate([
  // 1. Empezamos filtrando por el cliente 'ALFKI'
  { $match: { "customer_id_id": "ALFKI" } },

  // 2. Cruzamos con 'orders' para validar las fechas
  {
    $lookup: {
      from: "orders",
      localField: "orders",        // El array de pedidos en 'customers'
      foreignField: "order_id_id", // El id del pedido en 'orders'
      as: "customer_orders"
    }
  },
  { $unwind: "$customer_orders" },

  // 3. Filtramos por fecha (usando el formato string de tus documentos)
  { $match: { "customer_orders.order_date": { $gt: ISODate("1997-08-29T00:00:00Z") } } },

  // 4. Buscamos al EMPLEADO que gestiono ese pedido
  // Buscamos en la coleccion 'employees' donde su array 'orders' contenga el ID del pedido
  {
    $lookup: {
      from: "employees",
      localField: "customer_orders.order_id_id", // el pedido que buscamos
      foreignField: "orders", // el array de pedidos en 'employees'
      as: "order_employee"     
    }
  },
  { $unwind: "$order_employee" },

  // 5. Buscamos al JEFE de ese empleado
  // Buscamos al empleado cuyo array 'employees' contenga el ID del employee_order
  {
    $lookup: {
      from: "employees",
      localField: "order_employee.employee_id_id",
      foreignField: "employees", 
      as: "jefe"
    }
  },
  { $unwind: "$jefe" },

  // 6. SELECT DISTINCT: Agrupamos por el ID del jefe para eliminar duplicados
  {
    $group: {
      _id: "$jefe.employee_id_id",
      first_name: { $first: "$jefe.first_name" },
      last_name: { $first: "$jefe.last_name" }
    }
  },

  // 7. Formateamos la salida final
  {
    $project: {
      _id: 0,
      employee_id: "$_id",
      first_name: 1,
      last_name: 1
    }
  }
])

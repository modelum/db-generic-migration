db('Northwind').collection('shippers').aggregate([
  // 1. Filtramos por el transportista "Speedy Express"
  { 
    $match: { "company_name": "Speedy Express" } 
  },

  // 2. Descomponemos su array de orders para procesar cada ID individualmente
  { 
    $unwind: "$orders" 
  },

  // 3. JOIN con la colección "orders" para traer la fecha y los detalles
  {
    $lookup: {
      from: "orders",
      localField: "orders",           // El ID del array de shippers
      foreignField: "order_id_id",    // El ID en la colección orders
      as: "detalles_orden"
    }
  },
  { $unwind: "$detalles_orden" },

  // 4. JOIN con "customers" buscando qué cliente tiene este ID de orden en SU array
  {
    $lookup: {
      from: "customers",
      localField: "orders",           // El mismo ID de orden que estamos procesando
      foreignField: "orders",         // Buscamos dentro del array 'orders' de la colección customers
      as: "cliente"
    }
  },
  { $unwind: "$cliente" },

  // 5. Ordenamos por nombre de empresa y fecha de pedido
  {
    $sort: {
      "cliente.company_name": 1,
      "detalles_orden.order_date": 1
    }
  },

  // 6. Formateamos la salida final
  {
    $project: {
      _id: 0,
      company_name: "$cliente.company_name",
      order_id: "$detalles_orden.order_id_id",
      order_date: "$detalles_orden.order_date"
    }
  }
])

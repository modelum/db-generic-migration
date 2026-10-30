db('borrardb3').collection('shippers').aggregate([
  { 
    $match: { "company_name": "Speedy Express" } 
  },
  { 
    $unwind: "$orders" 
  },
  {
    $lookup: {
      from: "orders",
      localField: "orders",           // El ID del array de shippers
      foreignField: "order_id_id",    // El ID en la colección orders
      as: "detalles_orden"
    }
  },
  { $unwind: "$detalles_orden" },
  {
    $lookup: {
      from: "customers",
      localField: "orders",           // El mismo ID de orden que estamos procesando
      foreignField: "orders",         // Buscamos dentro del array 'orders' de la colección customers
      as: "cliente"
    }
  },
  { $unwind: "$cliente" },
  {
    $sort: {
      "cliente.company_name": 1,
      "detalles_orden.order_date": 1
    }
  },
  {
    $project: {
      _id: 0,
      company_name: "$cliente.company_name",
      order_id: "$detalles_orden.order_id_id",
      order_date: "$detalles_orden.order_date"
    }
  }
])

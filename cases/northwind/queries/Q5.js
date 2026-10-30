db('Northwind').collection('order_details').aggregate([
  // 1. Filtramos por el ID de la orden en order_details
  { 
    $match: { "order_details_fk_orders": 10248 } 
  },

  // 2. JOIN con "customers" para sacar el customer_id
  // Buscamos el ID de la orden (10248) dentro del ARRAY "orders" 
 // de cada cliente
  {
    $lookup: {
      from: "customers",
      localField: "order_details_fk_orders",
      foreignField: "orders",
      as: "info_cliente"
    }
  },
  { $unwind: "$info_cliente" },

  // 3. JOIN con "products" para sacar el product_name
  {
    $lookup: {
      from: "products",
      localField: "order_details_fk_products",
      foreignField: "product_id_id",
      as: "info_producto"
    }
  },
  { $unwind: "$info_producto" },

  // 4. JOIN con "categories" para sacar el category_name
  // Buscamos el ID del producto (ej: 1) dentro del ARRAY "products" de la categoría
  {
    $lookup: {
      from: "categories",
      localField: "order_details_fk_products",
      foreignField: "products",
      as: "info_categoria"
    }
  },
  { $unwind: "$info_categoria" },

  // 5. Formateamos la salida (SELECT)
  {
    $project: {
      _id: 0,
      order_id: "$order_details_fk_orders",
      customer_id: "$info_cliente.customer_id_id",
      product_name: "$info_producto.product_name",
      category_name: "$info_categoria.category_name"
    }
  }
])

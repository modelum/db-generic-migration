db('TPC-C-S-sinPK').collection('district').aggregate([
  {
    $lookup: {
      from: 'warehouse',
      localField: 'd_fk_w',
      foreignField: 'w_id_id',
      as: 'warehouse_info'
    }
  },
  {
    $unwind: '$warehouse_info'
  },
  {
    $lookup: {
      from: 'customer',
      localField: 'district_id',
      foreignField: 'c_fk_d',
      as: 'customers'
    }
  },
  {
    // Enforce the INNER JOIN semantics of the SQL query.
    $match: {
      'customers.0': { $exists: true }
    }
  },
  {
    $set: {
      customer_total: {
        $sum: '$customers.c_balance'
      },
      all_order_ids: {
        $reduce: {
          input: '$customers.orders',
          initialValue: [],
          in: {
            $concatArrays: [
              '$$value',
              { $ifNull: ['$$this', []] }
            ]
          }
        }
      }
    }
  },
  {
    $lookup: {
      from: 'orders',
      localField: 'all_order_ids',
      foreignField: 'orders_id',
      as: 'customer_orders'
    }
  },
  {
    // Enforce the INNER JOIN semantics of the SQL query.
    $match: {
      'customer_orders.0': { $exists: true }
    }
  },
  {
    $set: {
      sold_items: {
        $sum: '$customer_orders.o_ol_cnt'
      }
    }
  },
  {
    $project: {
      _id: 0,
      warehouse: '$warehouse_info.w_name',
      district: '$d_name',
      customer_total: { $toDouble: "$customer_total" }, //decimal128
      sold_items: 1
    }
  },
  {
    $sort: {
      sold_items: -1
    }
  }
], {
  allowDiskUse: true
});

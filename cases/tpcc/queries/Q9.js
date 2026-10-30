db('TPC-C-S').collection('stock').aggregate([
  {
    $match: {
      s_quantity: { $lt: 15 }
    }
  },
  {
    $lookup: {
      from: 'item',
      localField: 's_fk_i',
      foreignField: 'i_id_id',
      as: 'item_info'
    }
  },
  {
    $unwind: '$item_info'
  },
  {
    $lookup: {
      from: 'warehouse',
      localField: 's_fk_w',
      foreignField: 'w_id_id',
      as: 'warehouse_info'
    }
  },
  {
    $unwind: '$warehouse_info'
  },
  {
    $project: {
      _id: 0,
      item_id: '$s_fk_i',
      item_name: '$item_info.i_name',
      item_price: { $toDouble:'$item_info.i_price'},
      stock_quantity: '$s_quantity'
    }
  }

  // Optional for deterministic inspection:
  // { $sort: { item_id: 1 } }
], {
  allowDiskUse: true
});

db('TPC-C-S').collection('history').aggregate([
  {
    // Restrict the documents before splitting the composite identifier.
    $match: {
      h_fk_c: /^1_2_/
    }
  },
  {
    $set: {
      customer_key_parts: {
        $split: [
          { $arrayElemAt: ['$h_fk_c', 0] },
          '_'
        ]
      }
    }
  },
  {
    $set: {
      h_c_w_id: {
        $toInt: { $arrayElemAt: ['$customer_key_parts', 0] }
      },
      h_c_d_id: {
        $toInt: { $arrayElemAt: ['$customer_key_parts', 1] }
      },
      h_c_id: {
        $toInt: { $arrayElemAt: ['$customer_key_parts', 2] }
      }
    }
  },
  {
    $match: {
      h_c_w_id: 1,
      h_c_d_id: 2,
      h_c_id: { $lte: 10 }
    }
  },
  {
    $project: {
      _id: 0,
      h_c_id: 1,
      h_c_d_id: 1,
      h_c_w_id: 1,
      h_date: 1,
      h_amount: { $toDouble: "$h_amount" }
    }
  },
  {
    $sort: {
      h_c_id: 1
    }
  }
]);

db('MusicService-S').collection('listening').aggregate([
  { 
    $match: { plays_count: 10 } 
  },
  { // 2. Agrupar por usuario las canciones escuchadas
    $group: {
      _id: "$listening_fk_user",
      conteo_registros: { $sum: 1 } // Calcular COUNT(*)
    }
  },
  { // 3. HAVING COUNT(*) > 4 usuarios con >4 canciones escuchadas 10 veces
    $match: { 
      conteo_registros: { $gt: 4 } 
    }
  },
  { // 4. Hacemos el join para acceder a app_user
    $lookup: {
      from: "app_user",
      localField: "_id",
      foreignField: "user_id_id",
      as: "usuario_info"
    }
  },
  // 5. Filtrar los que no tengan coincidencia en app_user 
  // (Equivalente a la integridad referencial del IN)
  { 
    $match: { "usuario_info.0": { $exists: true } } 
  },
  
  { // 6. SELECT COUNT(*)
    $count: "total_usuarios"
  }
])

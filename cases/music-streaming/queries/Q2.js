db('MusicService-S').collection('song').aggregate([
  // 1. Filtrar por título de la canción (el WHERE)
  { $match: { title: "Song_12" } },

  { // 2. Cruzar con tabla asociativa para encontrar los estilos
    $lookup: {
      from: "song_style",
      localField: "song_id_id",           // El ID de la canción
      foreignField: "song_style_fk_song",
      as: "asociaciones"
    }
  },
  { $unwind: "$asociaciones" },

  {  // 3. Cruzar con musical_style para obtener nombre del estilo (M.name)
    $lookup: {
      from: "musical_style",
      localField: "asociaciones.song_style_fk_style",
      foreignField: "style_id_id",
      as: "info_estilo"
    }
  },
  { $unwind: "$info_estilo" },

  { $sort: { "info_estilo.name": 1 } },

  {
    $project: {
      _id: 0,
      title: 1,
      name: "$info_estilo.name"
    }
  }
])

db('MusicService-S').collection('app_user').aggregate([
  { 
    $match: {user_id_id: "b6dda633-e9f3-4de2-a63b-f0266f81bdbf"}
  },
  { $unwind: "$playlist" },
  { $unwind: "$playlist.playlist_song" },
  {
    $lookup: {
      from: "song",
      localField: "playlist.playlist_song.playlist_song_fk_song",
      foreignField: "song_id_id",
      as: "song_details"
    }
  },
  {     $unwind: "$song_details"   },
  {
    $sort: {
      "playlist.playlist_id_id": 1,
      "playlist.playlist_song.position_idx_id": 1
    }
  },
  {
    $project: {
      _id: 0,
      user_id: "$user_id_id",
      playlist_id: "$playlist.playlist_id_id",
      position: "$playlist.playlist_song.position_idx_id",
      song_id: "$playlist.playlist_song.playlist_song_fk_song",
      title: "$song_details.title"
    }
  }
])

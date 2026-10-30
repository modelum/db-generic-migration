db('MusicService-S').collection('user_app').aggregate([
  { 
    $match: {user_id_id: "a11eed24-f5cb-4714-a7a7-3a90cf4c2086"}
  },
  { $unwind: "$playlists" },
  {
    $lookup: {
      from: "playlist",
      localField: "playlists",
      foreignField: "playlist_id_id",
      as: "user_playlist"
    }
  },
  { $unwind: "$user_playlist" }, 
  { $unwind: "$user_playlist.playlist_song" }, 
{
    $lookup: {
      from: "song",
      localField: "user_playlist.playlist_song.playlist_song_fk_song",
      foreignField: "song_id_id",
      as: "song_details"
    }
  },
  { 
    $unwind: "$song_details" 
  },
  {
    $sort: {
      "user_playlist.playlist_id_id": 1,
      "user_playlist.playlist_song.position_idx_id": 1
    }
  },
  {
    $project: {
      _id: 0,
      user_id: "$user_id_id",
      playlist_id: "$user_playlist.playlist_id_id",
      position: "$user_playlist.playlist_song.position_idx_id",
      song_id: "$user_playlist.playlist_song.playlist_song_fk_song",
      title: "$song_details.title"
    }
  }
])

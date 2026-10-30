SELECT P.user_id, P.playlist_id, PS.position_idx, PS.song_id, S.title
FROM playlist P
    JOIN playlist_song PS
      ON P.user_id = PS.user_id AND P.playlist_id = PS.playlist_id
    JOIN song S ON PS.song_id = S.song_id  
WHERE P.user_id = 'b6dda633-e9f3-4de2-a63b-f0266f81bdbf' 
ORDER BY P.user_id, P.playlist_id, PS.position_idx;

SELECT S.title, M.name
FROM song_style SS -- Associative Table 
     JOIN musical_style M ON M.style_id = SS.style_id
     JOIN song S ON S.song_id = SS.song_id
WHERE S.title = 'Song_12' 
ORDER BY M.name;

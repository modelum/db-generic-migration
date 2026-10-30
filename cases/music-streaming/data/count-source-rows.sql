-- Read-only row inventory for a prepared Music Streaming source database.
-- Compare with paper-row-counts.json; retain observed counts for each new run.
SELECT 'app_user' AS table_name, COUNT(*) AS source_rows FROM public.app_user
UNION ALL SELECT 'listening', COUNT(*) FROM public.listening
UNION ALL SELECT 'most_recent_song', COUNT(*) FROM public.most_recent_song
UNION ALL SELECT 'playlist', COUNT(*) FROM public.playlist
UNION ALL SELECT 'playlist_song', COUNT(*) FROM public.playlist_song
UNION ALL SELECT 'song', COUNT(*) FROM public.song
UNION ALL SELECT 'song_style', COUNT(*) FROM public.song_style
UNION ALL SELECT 'musical_style', COUNT(*) FROM public.musical_style
ORDER BY table_name;

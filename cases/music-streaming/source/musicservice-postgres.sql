-- =====================================================
-- Relational Schema: Music Streaming Service
-- PostgreSQL version
-- =====================================================
CREATE DATABASE musicdb-M;
USE DATABASE musicdb-M;

DROP TABLE IF EXISTS most_recent_song;
DROP TABLE IF EXISTS listening;
DROP TABLE IF EXISTS song_style;
DROP TABLE IF EXISTS musical_style;
DROP TABLE IF EXISTS playlist_song;
DROP TABLE IF EXISTS playlist;
DROP TABLE IF EXISTS song;
DROP TABLE IF EXISTS app_user;

-- USERS
CREATE TABLE app_user (
    user_id        CHAR(36)    PRIMARY KEY,
    name           VARCHAR(80) NOT NULL UNIQUE,
    is_premium     BOOLEAN     NOT NULL DEFAULT FALSE,
    register_date  DATE        NOT NULL
);

-- SONGS
CREATE TABLE song (
    song_id     CHAR(36)     PRIMARY KEY,
    title       VARCHAR(200) NOT NULL,
    duration    NUMERIC(4,2) NOT NULL,   -- minutes (e.g. 03.45)
    artist      VARCHAR(120) NOT NULL,
    plays_count INTEGER      NOT NULL DEFAULT 0
);

-- MUSICAL STYLES
CREATE TABLE musical_style (
    style_id  CHAR(36)    PRIMARY KEY,
    name      VARCHAR(80) NOT NULL
);

-- SONG_STYLE (M:N without attributes)
CREATE TABLE song_style (
    song_id   CHAR(36) NOT NULL,
    style_id  CHAR(36) NOT NULL,
    PRIMARY KEY (song_id, style_id),
    FOREIGN KEY (song_id)  REFERENCES song(song_id) ON DELETE CASCADE,
    FOREIGN KEY (style_id) REFERENCES musical_style(style_id)
);

-- PLAYLIST (1 user : N playlists) - weak entity
CREATE TABLE playlist (
    playlist_id   CHAR(36) NOT NULL,
    user_id       CHAR(36) NOT NULL,
    name          VARCHAR(30),
    creation_date DATE     NOT NULL,
    PRIMARY KEY (user_id, playlist_id),
    FOREIGN KEY (user_id) REFERENCES app_user(user_id) ON DELETE CASCADE
);

-- PLAYLIST_SONG (weak entity, ordered list)
CREATE TABLE playlist_song (
    playlist_id  CHAR(36) NOT NULL,
    user_id      CHAR(36) NOT NULL,
    position_idx INTEGER  NOT NULL,
    song_id      CHAR(36) NOT NULL,
    PRIMARY KEY (user_id, playlist_id, position_idx),
    FOREIGN KEY (user_id, playlist_id)
        REFERENCES playlist(user_id, playlist_id)
        ON DELETE CASCADE,
    FOREIGN KEY (song_id)
        REFERENCES song(song_id)
);

-- LISTENING (M:N with attributes)
CREATE TABLE listening (
    user_id     CHAR(36) NOT NULL,
    song_id     CHAR(36) NOT NULL,
    plays_count INTEGER  NOT NULL,
    status      VARCHAR(10),
    PRIMARY KEY (user_id, song_id),
    FOREIGN KEY (user_id) REFERENCES app_user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES song(song_id)
);

-- MOST RECENTLY LISTENED SONGS (ordered list per user)
CREATE TABLE most_recent_song (
    user_id      CHAR(36) NOT NULL,
    position_idx INTEGER  NOT NULL,   -- 1 = most recent
    song_id      CHAR(36) NOT NULL,
    PRIMARY KEY (user_id, position_idx),
    FOREIGN KEY (user_id) REFERENCES app_user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES song(song_id)
);

-- Create indexes on foreign keys in all tables
CREATE INDEX idx_song_style_song_id 
  ON song_style (song_id);
CREATE INDEX idx_song_style_style_id 
  ON song_style (style_id);

CREATE INDEX idx_playlist_user_id
  ON playlist (user_id);

CREATE INDEX idx_playlist_song_playlist
  ON playlist_song (user_id, playlist_id);
CREATE INDEX idx_playlist_song_song
  ON playlist_song (song_id);

CREATE INDEX idx_listening_user_id
  ON listening (user_id);
CREATE INDEX idx_listening_song_id
  ON listening (song_id);  

CREATE INDEX idx_most_recent_song_user_id
  ON most_recent_song (user_id);
CREATE INDEX idx_most_recent_song_song_id
  ON most_recent_song (song_id);  

import uuid
import random
import datetime
import psycopg2
from psycopg2.extras import execute_batch
import sys
import psycopg2.extras

psycopg2.extras.register_uuid()


# -----------------------------
# Configuration
# -----------------------------

DATASET_CONFIG = {
    "S": {
        "users": 1000,
        "songs": 5000,
        "styles": 25,
        "playlists_per_user": 10,
        "songs_per_playlist": 20,
        "listens_per_user": 50,
        "recent_songs": 10
    },
    "M": {
        "users": 10000,
        "songs": 50000,
        "styles": 25,
        "playlists_per_user": 10,
        "songs_per_playlist": 20,
        "listens_per_user": 50,
        "recent_songs": 10
    },
    "L": {
        "users": 100000,
        "songs": 500000,
        "styles": 25,
        "playlists_per_user": 10,
        "songs_per_playlist": 20,
        "listens_per_user": 50,
        "recent_songs": 10
    }
}

DB_CONFIG = {
    "host": "localhost",
    "port": 5432,
    "dbname": "musicdb",
    "user": "music",
    "password": "music"
}

RANDOM_SEED = 42
BATCH_SIZE = 5_000

# -----------------------------
# Utility functions
# -----------------------------

def connect():
    return psycopg2.connect(**DB_CONFIG)

def random_date(start_year=2018, end_year=2024):
    start = datetime.date(start_year, 1, 1)
    end = datetime.date(end_year, 12, 31)
    return start + datetime.timedelta(days=random.randint(0, (end - start).days))

# -----------------------------
# Data generation
# -----------------------------

def truncate_all(cur):
    cur.execute("""
        TRUNCATE
          most_recent_song,
          listening,
          playlist_song,
          playlist,
          song_style,
          song,
          musical_style,
          app_user
        CASCADE;
    """)

def generate_styles(cur, n):
    rows = [(uuid.uuid4(), f"Style_{i}") for i in range(n)]
    execute_batch(
        cur,
        "INSERT INTO musical_style (style_id, name) VALUES (%s, %s)",
        rows,
        page_size=BATCH_SIZE
    )
    return [r[0] for r in rows]

def generate_songs(cur, n, styles):
    songs = []
    song_rows = []
    style_rows = []

    for i in range(n):
        sid = uuid.uuid4()
        songs.append(sid)
        song_rows.append((
            sid,
            f"Song_{i}",
            round(random.uniform(2.0, 6.0), 2),
            f"Artist_{random.randint(1, max(1, n // 100))}",
            random.randint(0, 10_000)
        ))

        for st in random.sample(styles, random.randint(1, 3)):
            style_rows.append((sid, st))

    execute_batch(
        cur,
        """INSERT INTO song (song_id, title, duration, artist, plays_count)
           VALUES (%s, %s, %s, %s, %s)""",
        song_rows,
        page_size=BATCH_SIZE
    )

    execute_batch(
        cur,
        "INSERT INTO song_style (song_id, style_id) VALUES (%s, %s)",
        style_rows,
        page_size=BATCH_SIZE
    )

    return songs

def generate_users(cur, n):
    users = []
    rows = []

    for i in range(n):
        uid = uuid.uuid4()
        users.append(uid)
        rows.append((
            uid,
            f"user_{i}",
            random.choice([True, False]),
            random_date()
        ))

    execute_batch(
        cur,
        """INSERT INTO app_user (user_id, name, is_premium, register_date)
           VALUES (%s, %s, %s, %s)""",
        rows,
        page_size=BATCH_SIZE
    )

    return users

def generate_playlists(cur, users, songs, cfg):
    playlist_rows = []
    playlist_song_rows = []

    for uid in users:
        for p in range(cfg["playlists_per_user"]):
            pid = uuid.uuid4()
            playlist_rows.append((pid, uid, f"Playlist_{p}", random_date()))

            for idx in range(1, cfg["songs_per_playlist"] + 1):
                sid = songs[random.randint(0, len(songs) - 1)]
                playlist_song_rows.append((pid, uid, idx, sid))

    execute_batch(
        cur,
        """INSERT INTO playlist (playlist_id, user_id, name, creation_date)
           VALUES (%s, %s, %s, %s)""",
        playlist_rows,
        page_size=BATCH_SIZE
    )

    execute_batch(
        cur,
        """INSERT INTO playlist_song
           (playlist_id, user_id, position_idx, song_id)
           VALUES (%s, %s, %s, %s)""",
        playlist_song_rows,
        page_size=BATCH_SIZE
    )

def generate_listening(cur, users, songs, cfg):
    listening_rows = []
    recent_rows = []

    for uid in users:
        listened = set()
        while len(listened) < cfg["listens_per_user"]:
            listened.add(songs[random.randint(0, len(songs) - 1)])

        listened = list(listened)

        for sid in listened:
            listening_rows.append((
                uid,
                sid,
                random.randint(1, 50),
                random.choice(["completed", "partial"])
            ))

        for idx, sid in enumerate(listened[:cfg["recent_songs"]], start=1):
            recent_rows.append((uid, idx, sid))

    execute_batch(
        cur,
        """INSERT INTO listening (user_id, song_id, plays_count, status)
           VALUES (%s, %s, %s, %s)""",
        listening_rows,
        page_size=BATCH_SIZE
    )

    execute_batch(
        cur,
        """INSERT INTO most_recent_song (user_id, position_idx, song_id)
           VALUES (%s, %s, %s)""",
        recent_rows,
        page_size=BATCH_SIZE
    )

# -----------------------------
# Main
# -----------------------------

def main(size):
    if size not in DATASET_CONFIG:
        raise ValueError("Dataset size must be one of: S, M, L")

    random.seed(RANDOM_SEED)
    cfg = DATASET_CONFIG[size]

    conn = connect()
    cur = conn.cursor()

    print(f"Generating dataset size {size}")
    truncate_all(cur)

    styles = generate_styles(cur, cfg["styles"])
    songs = generate_songs(cur, cfg["songs"], styles)
    users = generate_users(cur, cfg["users"])

    generate_playlists(cur, users, songs, cfg)
    generate_listening(cur, users, songs, cfg)

    conn.commit()
    cur.close()
    conn.close()

    print("Data generation completed.")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_data.py [S|M|L]")
        sys.exit(1)

    main(sys.argv[1])

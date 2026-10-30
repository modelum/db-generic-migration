const mongoose = require('mongoose');

const playlist_song = new mongoose.Schema({
	position_idx: {
		type: Number,
		required: true,
		unique: true
	},
	playlist_song_fk_song: {
		type: String,
		ref: 'song',
		required: true
	}
})
const most_recent_song = new mongoose.Schema({
	position_idx: {
		type: Number,
		required: true,
		unique: true
	},
	most_recent_song_fk_song: {
		type: String,
		ref: 'song',
		required: true
	}
})
const user_data = new mongoose.Schema({
	user_id: {
		type: String,
		required: true,
		unique: true
	},
	name: {
		type: String,
		required: true,
		unique: false
	},
	register_date: {
		type: String,
		required: true,
		unique: false
	},
	playlists: {
		type: [String],
		ref: 'playlist',
		required: true
	},
	most_recent_song: [most_recent_song]
})
const song = new mongoose.Schema({
	song_id: {
		type: String,
		required: true,
		unique: true
	},
	title: {
		type: String,
		required: true,
		unique: false
	},
	duration: {
		type: Number,
		required: true,
		unique: false
	},
	artist: {
		type: String,
		required: true,
		unique: false
	},
	plays_count: {
		type: Number,
		required: true,
		unique: false
	}
})
const musical_style = new mongoose.Schema({
	style_id: {
		type: String,
		required: true,
		unique: true
	},
	name: {
		type: String,
		required: true,
		unique: false
	}
})
const playlist = new mongoose.Schema({
	playlist_id: {
		type: String,
		required: true,
		unique: true
	},
	name: {
		type: String,
		required: true,
		unique: false
	},
	creation_date: {
		type: String,
		required: true,
		unique: false
	},
	playlist_song: [playlist_song]
})
const song_style = new mongoose.Schema({
	song_style: {
		type: Number,
		required: true,
		unique: true
	},
	song_style_fk_song: {
		type: String,
		ref: 'song',
		required: true
	},
	song_style_fk_style: {
		type: String,
		ref: 'musical_style',
		required: true
	}
})
const listening = new mongoose.Schema({
	plays_count: {
		type: Number,
		required: true,
		unique: false
	},
	status: {
		type: String,
		required: true,
		unique: false
	},
	listening: {
		type: Number,
		required: true,
		unique: true
	},
	listening_fk_user: {
		type: String,
		ref: 'user_data',
		required: true
	},
	listening_fk_song: {
		type: String,
		ref: 'song',
		required: true
	}
})

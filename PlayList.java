package model;
import java.util.ArrayList;
import java.util.Collections;

public class Playlist {
	private ArrayList<Song> playlist;
	private String playlistName;
	
	public Playlist(String playListName) {
		this.playlist = new ArrayList<>();
		this.playlistName = playListName;
	}
	
	// copy constructor
	public Playlist(Playlist other) {
		this.playlist = other.getPlaylistSongs();
		this.playlistName = other.playlistName;
	}
	
	public String getPlaylistName() {
		return this.playlistName;
	}
	
	public void addSong(Song song) {
		// adds song to playlist
		this.playlist.add(song);
	}
	
	public void removeSong(Song song) {
		// removes song from playlist
		this.playlist.remove(song);
	}
	
	public ArrayList<Song> getPlaylistSongs() {
		// returns copy of all playlist songs
		ArrayList<Song> copy = new ArrayList<>();
		for (Song song : playlist) {
			copy.add(new Song(song));
		}
		return copy;
	}
	
}
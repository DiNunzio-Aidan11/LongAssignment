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
	
	public Playlist(Playlist other) {
		this.playlist = other.playlist;
		this.playlistName = other.playlistName;
	}
	
	public String getPlaylistName() {
		return this.playlistName;
	}
	
	public void addSong(Song song) {
		this.playlist.add(song);
	}
	
	public void removeSong(Song song) {
		this.playlist.remove(song);
	}
	
	public ArrayList<Song> getPlaylistSongs() {
		return this.playlist;
	}
	
}

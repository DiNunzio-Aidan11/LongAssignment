package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

public class User extends LibraryModel {
    private String username;
    private String password; 

    public User(String username, String password) {
        super(); // Calls LibraryModel constructor
        this.username = username;
        this.password = password;
    }

	public String getPassword() {
		return password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void writeUserInfo() throws IOException {
		File file = new File(username + ".txt");
		
    	FileWriter fileWriter = new FileWriter(file, true);
    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		HashMap<Song, Rating> songData = new HashMap<>();
		HashMap<Playlist, ArrayList<Song>> playlistData = new HashMap<>();
		
		for (Song song : this.songs) {
			songData.put(song, song.getRating());
		}
		
		for (Playlist playlist : this.playlists) {
			playlistData.put(playlist, playlist.getPlaylistSongs());
		}
		
	}
	
}
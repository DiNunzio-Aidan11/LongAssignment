package model;

import java.util.ArrayList;
import java.util.Collections;

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
	
	public void saveToDatabase() {
		// TODO: saves to file
	}
	
	public void shuffle() {
		// this shuffles the library songs
		ArrayList<Song> songs = super.getLibrarySongs();
		Collections.shuffle(songs);
		super.setLibrarySongs(songs);
	}

	public void shufflePlaylist(String playlist) {
		ArrayList<Playlist> playlists = super.getLibraryPlaylists();
		for (Playlist pl : playlists) {
			if (pl.getPlaylistName().equals(playlist)) {
				ArrayList<Song> songs = pl.getPlaylistSongs();
				Collections.shuffle(songs);
				super.setPlaylist(songs, pl);
				return;
			}
		}
		System.out.println("playlist not found");
		
	}
    
    
}

package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

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
		
		for (Song song : this.getLibrarySongs()) {
			songData.put(song, song.getRating());
		}
		
		for (Playlist playlist : this.getLibraryPlaylists()) {
			playlistData.put(playlist, playlist.getPlaylistSongs());
		}

        bufferedWriter.write("Songs: (");
        ArrayList<String> lines = new ArrayList<>();

        for (Song song : songData.keySet()) {
            Rating rating = song.getRating();
            if (rating != null) {
                lines.add(song.getSongName() + " " + song.getRating() );
            }
            else {
                lines.add(song.getSongName() + " " + -1);
            }
        }

        bufferedWriter.write(String.join(",", lines) + ")\n");
        lines.clear();

        bufferedWriter.write("Favorites: (");

        for (Song song : this.getLibraryFavorites()) {
            lines.add(song.getSongName());
        }

        bufferedWriter.write(String.join(",", lines) + ")\n");
        lines.clear();

        bufferedWriter.write("Top Rated: (");

        for (Song song : this.topRated) {
            lines.add(song.getSongName());
        }

        bufferedWriter.write(String.join(",", lines) + ")\n");
        lines.clear();

        bufferedWriter.write("Playlists: (");

        for (Playlist playlist : this.getLibraryPlaylists()) {
            System.out.println("Playlist " + playlist.getPlaylistName() + " has " + playlist.getPlaylistSongs().size() + " songs");
            bufferedWriter.write(playlist.getPlaylistName() + ": [");
            for (Song song : playlist.getPlaylistSongs()) {
                lines.add(song.getSongName());
            }
            bufferedWriter.write(String.join(",", lines) + "]");
            lines.clear();
        }

        bufferedWriter.write(")\n");

        bufferedWriter.write("Albums: (");

        for (Album album : this.getLibraryAlbums()) {
            lines.add(album.getAlbumName());
        }

        bufferedWriter.write(String.join(",", lines) + ")\n");
        lines.clear();

        bufferedWriter.write("Artists: (");

        for (Artist artist : this.getLibraryArtists()) {
            lines.add(artist.getArtistName());
        }

        bufferedWriter.write(String.join(",", lines) + ")\n");
        lines.clear();

        bufferedWriter.close();
        fileWriter.close();
    }

    public void initializeDatabase() throws IOException {
        File file = new File(username + ".txt");
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if (line.startsWith("Songs: ")) {
                
            }
        }

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

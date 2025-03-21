package model;

import java.util.ArrayList;

/*
 * Class Album 
 * This class stores information about the album name genre etc also holding an array list of Song objects
 */

public class Album {
	private ArrayList<Song> songs = new ArrayList<>();
	private final String albumName;
    private final String albumGenre;
    private final String albumYear;
    private final String artistName;
	
	// main constructor
	// @pre albumName != ""
	public Album(String albumName, String albumGenre, String albumYear, String artistName) {
		this.albumName = albumName;
        this.albumGenre = albumGenre;
        this.albumYear = albumYear;
        this.artistName = artistName;
	}
	
	// copy constructor
	public Album(Album other) {
		this.albumName = other.albumName;
        this.albumGenre = other.albumGenre;
        this.albumYear = other.albumYear;
        this.artistName = other.artistName;
        this.songs = other.getAllSongs(); // is copied so no escaping references
	}
	
	public String getAlbumName() {
		return this.albumName;
	}

    public String getAlbumGenre() {
        return this.albumGenre;
    }

    public String getAlbumYear() {
        return this.albumYear;
    }
    
    public String getArtistName() {
    	return this.artistName;
    }
	
	public void addSong(String songName) {
		/*
		 * Adds a song object given the name of the song 
		 */
		int seen = 0;
		for (Song song : songs) {
			if (song.getSongName() == songName) {
				seen = 1;
				break;
			}
		}
		if (seen == 1) {
			System.out.println("Error duplicate song: " + songName + " in album: " + this.albumName);
		}
		else {
			songs.add(new Song(songName, this.albumName, this.artistName));
		}
		
	}
	public Song getCertainSong(String songName) {
		/*
		 * returns a copy of the song object if found in the album otherwise returns null
		 * used for song by title & song by artist
		 */
		for (Song song : songs) {
			if (song.getSongName().equals(songName)) {
				return song;
			}
		}
		return null;
		
	}
	public boolean songInAlbum(Song song) {
		// returns if the song object is in the album
		for (Song allsongs : songs) {
			if (song.equals(allsongs)) {
				return true;
			}
			
		}
		return false;
		
	}
	public ArrayList<Song> getAllSongs() {
		/*
		 * returns a copy of all the song objects in an album
		 * used for song by title 
		 */
		 ArrayList<Song> all = new ArrayList<Song>();
		 for (Song song : songs) {
			 all.add(new Song(song)); // returns copy of songs
		 }
		 return all;
	}
	
	
}
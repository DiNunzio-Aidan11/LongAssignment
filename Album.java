package model;

import java.util.ArrayList;

public class Album {
	private ArrayList<Song> songs = new ArrayList<>();
	private final String albumName;
    private final String albumGenre;
    private final String albumYear;
	
	// main constructor
	// @pre albumName != ""
	public Album(String albumName, String albumGenre, String albumYear) {
		this.albumName = albumName;
        this.albumGenre = albumGenre;
        this.albumYear = albumYear;
	}
	
	public Album(Album other) {
		this.albumName = other.albumName;
        this.albumGenre = other.albumGenre;
        this.albumYear = other.albumYear;
		this.songs = other.getAllSongs();
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
	
	public void addSong(String songName) {
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
			songs.add(new Song(songName));
		}
		
	}
	public Song getCertainSong(String songName) { // used for song by title & song by artist
		for (Song song : songs) {
			if (song.getSongName() == songName) {
				return new Song(song); // returns copy of song
			}
		}
		System.out.println("Error can't find song");
		return null;
		
	}
	public ArrayList<Song> getAllSongs() { // used for song by title 
		 ArrayList<Song> all = new ArrayList<Song>();
		 for (Song song : songs) {
			 all.add(new Song(song)); // returns copy of songs
		 }
		 return all;
	}
	
	
}

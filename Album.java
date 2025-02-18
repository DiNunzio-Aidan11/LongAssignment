package model;

import java.util.ArrayList;

public class Album {
	private ArrayList<Song> songs = new ArrayList<>();
	private String albumName;
	
	public Album(String albumName) {
		this.albumName = albumName;
	}
	
	public String getAlbumName() {
		return this.albumName;
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
				return song;
			}
		}
		System.out.println("Error can't find song");
		return null;
		
	}
//	public ArrayList<Song> getAllSongs() { // used for song by artist 
//		return this.songs; //TODO: make copy
//	}
	
	
}


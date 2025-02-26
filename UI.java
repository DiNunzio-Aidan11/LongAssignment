package view;
import java.util.ArrayList;

import model.Album;
import model.Artist;
import model.PlayList;
import model.Rating;
import model.Song;
import model.MusicStore;

public class UI {
	private ArrayList<PlayList> playLists = new ArrayList<>();
	
	public void userInterface() {
		System.out.println("Commands");
		
		
	}
	
	
	//search for information from the music store
	public void searchByTitle(String songName, Source a) {
		// song that is in the database: print the song title, the artist, and the album it’s on
		
		// for loop that goes through every artist even if found song
		
	}
	public void searchByArtist(String artistName, Source a) {
		// song that is in the database: print the song title, the artist, and the album it’s on
		
		// for loop 
		
	}
	public void searchByAlbumTitle(String albumName, Source a) {
		// album: print the album information and a list of the songs in the appropriate order
		
	}
	public void searchByAlbumArtist(String artistName, Source a) {
		// album: print the album information and a list of the songs in the appropriate order
		
	}
	
	
	
	// add something to the library
	public boolean addSongToLibrary(Song toBeAdded) {
		return true;
	}
	
	public boolean addAlbumToLibrary(Album toBeAdded) {
		return true;
	}
	
	
	
	// get a list of items from the library
	public void getLibrarySongTitles(String songName) {
		// for all songs in library call search by title 
	}
	
	
	
	public boolean createPlayList(String playListName) {		
		return true;
	}
	
	public boolean addToPlayList(String playListName) {
		return true;
	}
	
	public boolean removeFromPlayList(String playListName) {
		return true;
	}
	
	public void rateSong(Song a, Rating r) {
		
	}

}

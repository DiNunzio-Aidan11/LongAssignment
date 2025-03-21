package view;

import java.util.ArrayList;
import model.Album;
import model.Artist;
import model.LibraryModel;
import model.Playlist;
import model.Rating;
import model.Song;
import model.User;

public class UI {
	private ArrayList<Artist> store;
	private User user;

	public UI(ArrayList<Artist> storeObject) {
		this.store = storeObject;
	}

	// search for information from the music store
	public ArrayList<Song> searchByTitle(String songName) {
		ArrayList<Song> foundSongs = new ArrayList<>();
		ArrayList<Artist> database;
		database = new ArrayList<Artist>(store);
		for (Artist artist : database) {
			for (Album album : artist.getAllAlbums()) {
				Song song = album.getCertainSong(songName);
				if (song != null) {
					foundSongs.add(song);
				}
			}
		}
		return foundSongs;
	}

	public ArrayList<Song> searchByArtist(String artistName) {
		// song that is in the database: print the song title, the artist, and the album
		ArrayList<Song> foundSongs = new ArrayList<>();
		ArrayList<Artist> database;
		database = new ArrayList<Artist>(store);
		for (Artist artist : database) {
			if (artist.getArtistName().equals(artistName)) {
				for (Album album : artist.getAllAlbums()) {
					for (Song song : album.getAllSongs())
						foundSongs.add(song);
				}
			}
		}
		return foundSongs;
	}

	public ArrayList<Album> searchByAlbumTitle(String albumName) {
		// album: print the album information and a list of the songs in the appropriate
		// order
		ArrayList<Artist> database;
		ArrayList<Album> foundAlbums = new ArrayList<>();
		database = new ArrayList<Artist>(store);
		for (Artist artist : database) {
			Album album = artist.getCertainAlbum(albumName);
			if (album != null) {
				foundAlbums.add(album);
			}
		}
		return foundAlbums;
	}

	public ArrayList<Album> searchByAlbumArtist(String artistName) {
		ArrayList<Artist> database;
		ArrayList<Album> foundAlbums = new ArrayList<>();
		database = new ArrayList<Artist>(store);
		for (Artist artist : database) {
			if (artist.getArtistName().equals(artistName)) {
				for (Album album : artist.getAllAlbums()) {
					foundAlbums.add(album);
				}
			}
		}
		return foundAlbums;
	}
	
	public Artist searchArtistFromSong (String artistName) {
		for (Artist artist : store) {
			if (artist.getArtistName().equals(artistName)) {
				return artist;
			}
		}
		return null;
	}
	
	public Artist searchArtistFromAlbum (String artistName) {
		for (Artist artist : store) {
			if (artist.getArtistName().equals(artistName)) {
				return artist;
			}
		}
		return null;
	}

	// add something to the user library
	public void addSongToLibrary(Song toBeAdded) {
		this.user.addSong(toBeAdded);
	}

	public void addAlbumToLibrary(Album toBeAdded) {
		this.user.addAlbum(toBeAdded);
	}
	
	public void addArtistToLibrary(Artist artist) {
		this.user.addArtist(artist);
	}

	// get a list of items from the library
	public ArrayList<Song> getLibrarySongs() {
		return this.user.getLibrarySongs();
	}

	public ArrayList<Artist> getLibraryArtists() {
		return this.user.getLibraryArtists();

	}

	public ArrayList<Album> getLibraryAlbums() {
		return this.user.getLibraryAlbums();
	}

	public ArrayList<Playlist> getLibraryPlaylists() {
		return this.user.getLibraryPlaylists();

	}

	public ArrayList<Song> getLibraryFavorites() {
		return this.user.getLibraryFavorites();

	}

	public void createPlaylist(String playlistName) {
		this.user.addPlaylist(playlistName);
	}

	public void addToPlaylist(Song song, Playlist playlist) {
		this.user.addSongToPlaylist(song, playlist);
	}

	public void removeFromPlaylist(Song song, Playlist playlist) {
		this.user.removeSongFromPlaylist(song, playlist);
	}
	
	public void removeSong(Song song) {
		this.user.removeSong(song);
	}
	
	public void removeAlbum(Album album) {
		this.user.removeAlbum(album);
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void shuffleSongs() {
		this.user.shuffle();
	}
	
	public void saveChanges() {
		// userSaveChanges()... 
	}

	public void rateSong(Song a, Rating r) {
		if (r == Rating.FIVESTAR) {
			this.user.addToFavorites(a);
			this.user.addToTopRated(a);
		}
		else if (r == Rating.FOURSTAR) {
			this.user.addToTopRated(a);
		}
		a.setRating(r);
	}

	public void markFavorite(Song a, boolean x) {
		a.setFavorite(x);
	}

}
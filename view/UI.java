package view;

import java.util.ArrayList;
import model.Album;
import model.Artist;
import model.LibraryModel;
import model.Playlist;
import model.Rating;
import model.Song;

public class UI {
	private ArrayList<Artist> store;
	private LibraryModel library;

	public UI(ArrayList<Artist> storeObject) {
		this.store = storeObject;
		this.library = new LibraryModel();
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

	// add something to the library
	public void addSongToLibrary(Song toBeAdded) {
		this.library.addSong(toBeAdded);
	}

	public void addAlbumToLibrary(Album toBeAdded) {
		this.library.addAlbum(toBeAdded);
	}
	
	public void addArtistToLibrary(Artist artist) {
		this.library.addArtist(artist);
	}

	// get a list of items from the library
	public ArrayList<Song> getLibrarySongs() {
		return library.getLibrarySongs();
	}

	public ArrayList<Artist> getLibraryArtists() {
		return library.getLibraryArtists();

	}

	public ArrayList<Album> getLibraryAlbums() {
		return library.getLibraryAlbums();
	}

	public ArrayList<Playlist> getLibraryPlaylists() {
		return library.getLibraryPlaylists();

	}

	public ArrayList<Song> getLibraryFavorites() {
		return library.getLibraryFavorites();

	}

	public void createPlaylist(String playlistName) {
		library.addPlaylist(playlistName);
	}

	public void addToPlaylist(Song song, Playlist playlist) {
		library.addSongToPlaylist(song, playlist);
	}

	public void removeFromPlaylist(Song song, Playlist playlist) {
		library.removeSongFromPlaylist(song, playlist);
	}

	public void rateSong(Song a, Rating r) {
		if (r == Rating.FIVESTAR) {
			a.setFavorite(true);
			this.library.addToFavorites(a);
		}
		a.setRating(r);
	}

	public void markFavorite(Song a, boolean x) {
		a.setFavorite(x);
	}

}

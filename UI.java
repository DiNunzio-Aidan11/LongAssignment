package view;

import java.util.ArrayList;

import model.Album;
import model.Artist;
import model.PlayList;
import model.Rating;
import model.Song;

public class UI {
	private ArrayList<PlayList> playLists;
	private ArrayList<Artist> store;
	private ArrayList<Artist> library;

	public UI(ArrayList<Artist> storeObject) {
		this.store = storeObject;
	}

	public void userInterface() {
		System.out.println("Commands");
		
	}

	// search for information from the music store
	public void searchByTitle(String songName, Source a) {
		// song that is in the database: print the song title, the artist, and the album
		boolean empty = true;
		ArrayList<Artist> database;
		if (a == Source.STORE) {
			database = new ArrayList<Artist>(store);
		} else {
			database = new ArrayList<Artist>(library);
		}

		for (Artist artist : database) {
			for (Album album : artist.getAllAlbums()) {
				Song song = album.getCertainSong(songName);
				if (song != null) {
					empty = false;
					System.out.println(songName + ", " + artist.getArtistName() + ", " + album.getAlbumName());
				}
			}
		}
		if (empty) {
			System.out.println("No Song: " + songName + " Found");
		}
		System.out.println();

	}

	public void searchByArtist(String artistName, Source a) {
		// song that is in the database: print the song title, the artist, and the album
		boolean empty = true;
		ArrayList<Artist> database;
		if (a == Source.STORE) {
			database = new ArrayList<Artist>(store);
		} else {
			database = new ArrayList<Artist>(library);
		}
		for (Artist artist : database) {
			if (artist.getArtistName() == artistName) {
				for (Album album : artist.getAllAlbums()) {
					for (Song song : album.getAllSongs())
						System.out.println(song.getSongName() + ", " + artistName + ", " + album.getAlbumName());
					empty = false;
				}
			}
		}
		if (empty) {
			System.out.println("No Songs by: " + artistName + " Found");
		}
		System.out.println();

	}

	public void searchByAlbumTitle(String albumName, Source a) {
		// album: print the album information and a list of the songs in the appropriate
		// order
		boolean empty = true;
		ArrayList<Artist> database;
		if (a == Source.STORE) {
			database = new ArrayList<Artist>(store);
		} else {
			database = new ArrayList<Artist>(library);
		}
		for (Artist artist : database) {
			Album album = artist.getCertainAlbum(albumName);
			if (album != null) {
				empty = false;
				System.out.println("Album: " + album.getAlbumName());
				System.out.println("Artist: " + artist.getArtistName());
				System.out.println("Released: " + album.getAlbumYear());
				System.out.println("Genre: " + album.getAlbumGenre());
				System.out.println("Songs:");
				for (Song song : album.getAllSongs()) {
					System.out.println("\t" + song.getSongName());
				}
				System.out.println();
			}
		}
		if (empty) {
			System.out.println("No album: " + albumName + " found");
		}
		System.out.println();

	}

	public void searchByAlbumArtist(String artistName, Source a) {
		// album: print the album information and a list of the songs in the appropriate
		// order

		boolean empty = true;
		ArrayList<Artist> database;
		if (a == Source.STORE) {
			database = new ArrayList<Artist>(store);
		} else {
			database = new ArrayList<Artist>(library);
		}
		for (Artist artist : database) {
			if (artist.getArtistName() == artistName) {
				for (Album album : artist.getAllAlbums()) {
					empty = false;
					System.out.println("Album: " + album.getAlbumName());
					System.out.println("Artist: " + artist.getArtistName());
					System.out.println("Released: " + album.getAlbumYear());
					System.out.println("Genre: " + album.getAlbumGenre());
					System.out.println("Songs:");
					for (Song song : album.getAllSongs()) {
						System.out.println("\t" + song.getSongName());
					}
					System.out.println();
				}

			}
		}

		if (empty) {
			System.out.println("No albums found for: " + artistName);
		}
		System.out.println();

	}

	// add something to the library
	public boolean addSongToLibrary(Song toBeAdded) {
		return true;
	}

	public boolean addAlbumToLibrary(Album toBeAdded) {
		return true;
	}

	// get a list of items from the library
	public ArrayList<String> getLibrarySongTitles() {
		// for all songs in library call search by title
		return null;
	}

	public ArrayList<Artist> getLibraryArtists() {
		return null;

	}

	public ArrayList<Album> getLibraryAlbum() {
		return null;

	}

	public ArrayList<PlayList> getLibraryPlaylists() {
		return null;

	}

	public ArrayList<Song> getLibraryFavorites() {
		return null;

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
		if (r == Rating.FIVESTAR) {
			a.setFavorite(true);
		}
		a.setRating(r);
	}

	public void markFavorite(Song a, boolean x) {
		a.setFavorite(x);
	}

}

package model;
import java.util.ArrayList;

public class LibraryModel {
	private ArrayList<Song> songs;
	private ArrayList<Song> favorites;
	private ArrayList<Playlist> playlists;
	private ArrayList<Album> albums;
	private ArrayList<Artist> artists;
	
	public LibraryModel() {
		this.songs = new ArrayList<>();
		this.favorites = new ArrayList<>();
		this.playlists = new ArrayList<>();
		this.albums = new ArrayList<>();
		this.artists = new ArrayList<>();
	}
	
	// copy constructor
	public LibraryModel(LibraryModel other) {
		this.songs = other.getLibrarySongs();
		this.favorites = other.getLibraryFavorites();
		this.playlists = other.getLibraryPlaylists();
		this.albums = other.getLibraryAlbums();
		this.artists = other.getLibraryArtists();
	}
	
	public void addPlaylist(String name) {
		// adds in the playlist to the library if it is not a duplicate
		boolean seen = false;
		for (Playlist playlist : playlists) {
			if (playlist.getPlaylistName() == name) {
				seen = true;
				break;
			}
		}
		if (seen) {
			System.out.println("cant have playlist with the same name in library");
			return;
		}
		Playlist playlist = new Playlist(name);
		this.playlists.add(playlist);
	}
	
	public void addSong(Song song) {
		// adds in the song to the library if it is not a duplicate
		boolean seen = false;
		for (Song cur : songs) {
			if (cur.getSongName() == song.getSongName() && cur.getArtistName() == song.getArtistName()) {
				seen = true;
			}
		}
		if (seen) {
			System.out.println("cant have duplicate song in library");
			return;
		}
		
		if (song.isFavorite() == true) {
			this.favorites.add(song);
		}
		this.songs.add(song);
	}
	
	public void addAlbum(Album album) {
		// adds in the album to the library if it is not a duplicate
		boolean seen = false;
		for (Album cur : albums) {
			if (cur.getAlbumName() == album.getAlbumName() && cur.getArtistName() == album.getArtistName()) {
				seen = true;
				break;
			}
		}
		if (seen) {
			System.out.println("cant have duplicate album");
			return;
		}
		this.albums.add(album);
	}
	
	public void addArtist(Artist artist) {
		// adds in the artist to the library if it is not a duplicate
		boolean seen = false;
		for (Artist cur : artists) {
			if (cur.getArtistName() == artist.getArtistName()) {
				seen = true;
				break;
			}
		}
		if (seen) {
			System.out.println("cant have duplicate author");
			return;
		}
		this.artists.add(artist);
	}
	
	public void addSongToPlaylist(Song song, Playlist playlist2) {
		// adds in the song to the playlist if it is not a duplicate
		for (Playlist playlist : playlists) {
			if (playlist.getPlaylistName().equals(playlist2.getPlaylistName())) {
				ArrayList<Song> songs = playlist2.getPlaylistSongs();
				boolean seen = false;
				for (Song cur : songs) {
					if (cur.getArtistName() == song.getArtistName() && cur.getSongName() == song.getSongName()) {
						seen = true;
					}
				}
				if (seen) {
					System.out.println("cant have duplicate song in playlist");
					return;
				}
				
				playlist2.addSong(song);;
				return;
			}
		}
		System.out.println("Playlist not found");
		return;
	}
	
	public void removeSongFromPlaylist(Song song, Playlist playlist2) {
		// removes a song in the playlist if found
		for (Playlist playlist : playlists) {
			if (playlist.getPlaylistName().equals(playlist2.getPlaylistName())) {
				playlist.removeSong(song);
				return;
			}
		}
		System.out.println("Song not found in playlist");
		return;
	}
	
	public void addToFavorites(Song song) {
		// adds a song as favorite if found
		boolean seen = false;
		for (Song cur : favorites) {
			if (cur.getArtistName() == song.getArtistName() && cur.getSongName() == song.getSongName()) {
				seen = true;
			}
			if (seen) {
				System.out.println("cant have duplicate song in favorites");
				return;
			}
		}
		this.favorites.add(song);
	}
	
	public ArrayList<Playlist> getLibraryPlaylists() {
		// returns a copy of Library Playlists
		ArrayList<Playlist> copy = new ArrayList<>();
		for(Playlist playlist : playlists) {
			copy.add(new Playlist(playlist));
		}
		return copy;
	}
	
	public ArrayList<Song> getLibrarySongs() {
		// returns a copy of Library Songs
		ArrayList<Song> copy = new ArrayList<>();
		for(Song song : songs) {
			copy.add(new Song(song));
		}
		return copy;
	}
	
	public ArrayList<Album> getLibraryAlbums() {
		// returns a copy of Library Albums
		ArrayList<Album> copy = new ArrayList<>();
		for(Album album : albums) {
			copy.add(new Album(album));
		}
		return copy;
	}
	
	public ArrayList<Artist> getLibraryArtists() {
		// returns a copy of Library Artists
		ArrayList<Artist> copy = new ArrayList<>();
		for(Artist artist : artists) {
			copy.add(new Artist(artist));
		}
		return copy;
	}
	
	public ArrayList<Song> getLibraryFavorites() {
		// returns a copy of Library favorites
		ArrayList<Song> copy = new ArrayList<>();
		for(Song song : favorites) {
			copy.add(new Song(song));
		}
		return copy;
	}
}

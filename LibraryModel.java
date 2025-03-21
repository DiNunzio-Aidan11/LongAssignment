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
			return;
		}
		
		if (song.isFavorite() == true) {
			this.favorites.add(song);
		}
		this.songs.add(song);
	}
	
	public void removeSong(Song song) {
		for (Album album : this.albums) {
			if (album.songInAlbum(song) == true && album.getAllSongs().size() == 1) {
				removeAlbum(album);
				return;
			}
		}
		this.songs.remove(song);
	}
	
	public void removeAlbum(Album album) {
		for (Song song : album.getAllSongs()) {
			this.songs.remove(song);
		}
		this.albums.remove(album);
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
	
	public void toggleFavorite(Song song) {
		if (song.isFavorite()) {
			song.setFavorite(false);
			this.favorites.remove(song);
			return;
		}
		song.setFavorite(true);
		this.favorites.add(song);
	}
	
	public void rateSong(Song song, Rating rating) {
		song.setRating(rating);
		if (rating == Rating.FIVESTAR) {
			if(!song.isFavorite()) {
				toggleFavorite(song);
			}
		}
	}
	
	public ArrayList<Playlist> getLibraryPlaylists() {
		return playlists;
	}
	
	public ArrayList<Song> getLibrarySongs() {
		return songs;
	}
	
	public void setLibrarySongs(ArrayList<Song> songs) {
		// allows for shuffle 
		this.songs = songs;
	}
	
	public void setPlaylist(ArrayList<Song> songs, Playlist pl) {
		// allows for shuffle 
		pl.setPlaylist(songs);
		this.songs = songs;
	}
	
	public ArrayList<Album> getLibraryAlbums() {
		return albums;
	}
	
	public ArrayList<Artist> getLibraryArtists() {
		return artists;
	}
	
	public ArrayList<Song> getLibraryFavorites() {
		return favorites;
	}
	
}

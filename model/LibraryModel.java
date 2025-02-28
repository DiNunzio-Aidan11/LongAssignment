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
	
	public LibraryModel(LibraryModel other) {
		other.songs = this.songs;
		other.favorites = this.favorites;
		other.playlists = this.playlists;
		other.albums = this.albums;
		other.artists = this.artists;
	}
	
	public void addPlaylist(String name) {
		Playlist playlist = new Playlist(name);
		this.playlists.add(playlist);
	}
	
	public void addSong(Song song) {
		if (song.isFavorite() == true) {
			this.favorites.add(song);
		}
		this.songs.add(song);
	}
	
	public void addAlbum(Album album) {
		this.albums.add(album);
	}
	
	public void addArtist(Artist artist) {
		this.artists.add(artist);
	}
	
	public void addSongToPlaylist(Song song, Playlist playlist2) {
		for (Playlist playlist : playlists) {
			if (playlist.getPlaylistName().equals(playlist2)) {
				playlist.addSong(song);
				return;
			}
		}
		System.out.println("Playlist not found\n");
		return;
	}
	
	public void removeSongFromPlaylist(Song song, Playlist playlist2) {
		for (Playlist playlist : playlists) {
			if (playlist.getPlaylistName().equals(playlist2)) {
				playlist.removeSong(song);
				return;
			}
		}
		System.out.println("Song not found in playlist\n");
		return;
	}
	
	public void addToFavorites(Song song) {
		this.favorites.add(song);
	}
	
	public ArrayList<Playlist> getLibraryPlaylists() {
		return this.playlists;
	}
	
	public ArrayList<Song> getLibrarySongs() {
		return this.songs;
	}
	
	public ArrayList<Album> getLibraryAlbums() {
		return this.albums;
	}
	
	public ArrayList<Artist> getLibraryArtists() {
		return this.artists;
	}
	
	public ArrayList<Song> getLibraryFavorites() {
		return this.favorites;
	}
}

package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LibraryModel {
	protected ArrayList<Song> songs;
	protected ArrayList<Song> favorites;
	protected ArrayList<Playlist> playlists;
	protected ArrayList<Song> topRated;
	protected ArrayList<Album> albums;
	protected ArrayList<Artist> artists;
	protected HashMap<String, Integer> genreCount;
    protected ArrayList<Song> recentlyPlayed;
    protected HashMap<Song, Integer> frequentlyPlayed;
	
	
	public LibraryModel() {
		this.songs = new ArrayList<>();
		this.favorites = new ArrayList<>();
		this.playlists = new ArrayList<>();
		this.albums = new ArrayList<>();
		this.artists = new ArrayList<>();
		this.genreCount = new HashMap<>();
		this.topRated = new ArrayList<>();
        this.recentlyPlayed = new ArrayList<>();
        this.frequentlyPlayed = new HashMap<>();
		this.addPlaylist("Favorites");
		this.addPlaylist("Top Rated");
	}
	
	// copy constructor
	public LibraryModel(LibraryModel other) {
		this.songs = other.getLibrarySongs();
		this.favorites = other.getLibraryFavorites();
		this.playlists = other.getLibraryPlaylists();
		this.albums = other.getLibraryAlbums();
		this.artists = other.getLibraryArtists();
		this.genreCount = new HashMap<>();
		this.topRated = new ArrayList<>();
        this.recentlyPlayed = other.recentlyPlayed;
        this.frequentlyPlayed = other.frequentlyPlayed;
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
	
	public void incrementGenreCount(String genre) {
		if (this.genreCount.containsKey(genre)) {
			this.genreCount.put(genre, this.genreCount.get(genre) + 1);
			checkGenreCount(genre);
		}
		else {
			this.genreCount.put(genre, 1);
		}
	}
	
	public void checkGenreCount(String genre) {
		if (this.genreCount.get(genre) == 10) {
			Playlist playlist = new Playlist(genre);
			for (Song song : this.songs) {
				String albumName = song.getAlbumName();
				for (Album album : this.albums) {
					if (album.getAlbumName().equals(albumName) && album.getAlbumGenre().equals(genre)) {
						playlist.addSong(song);
					}
				}
			}
		}
	}
	
	public List<Song> getMostFrequentlyPlayed() {
	    return frequentlyPlayed.entrySet()
	        .stream()
	        .sorted(Map.Entry.<Song, Integer>comparingByValue().reversed())
	        .limit(10)
	        .map(Map.Entry::getKey)
	        .collect(Collectors.toList());
	}
	
	
    public void playSong(Song song) {
        frequentlyPlayed.put(song, frequentlyPlayed.getOrDefault(song, 0) + 1);
    }

    public ArrayList<Song> getRecentlyPlayed() {
        return this.recentlyPlayed;
    }
    
    public void addToRecentlyPlayed(Song song) {
        if (this.recentlyPlayed.size() == 10) {
            recentlyPlayed.remove(0);
        }
        recentlyPlayed.add(song);
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
		this.frequentlyPlayed.put(song, 0);
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
	
//	public void toggleFavorite(Song song) {
//		if (song.isFavorite()) {
//			song.setFavorite(false);
//			this.favorites.remove(song);
//			return;
//		}
//		song.setFavorite(true);
//		this.favorites.add(song);
//	}
//	
//	public void rateSong(Song song, Rating rating) {
//		song.setRating(rating);
//		if (rating == Rating.FIVESTAR) {
//			if(!song.isFavorite()) {
//				toggleFavorite(song);
//			}
//		}
//	}
	
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
	
	public void addToFavorites(Song song) {
		// adds a song as favorite if found
		boolean seen = false;
		for (Song cur : favorites) {
			if (cur.getArtistName().equals(song.getArtistName()) && cur.getSongName().equals(song.getSongName())) {
				seen = true;
			}
			if (seen) {
				return;
			}
		}
		this.favorites.add(song);
		this.addSongToPlaylist(song, this.playlists.get(0));
	}
	
	public void addToTopRated(Song song) {
		boolean seen = false;
		for (Song cur : topRated) {
			if (cur.getArtistName().equals(song.getArtistName()) && cur.getSongName().equals(song.getSongName())) {
				seen = true;
			}
			if (seen) {
				return;
			}
		}
		this.topRated.add(song);
		this.addSongToPlaylist(song, this.playlists.get(1));
	}
	
}

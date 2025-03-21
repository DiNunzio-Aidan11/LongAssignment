package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.Album;
import model.Artist;
import model.LibraryModel;
import model.Playlist;
import model.Song;

public class TestLibraryModel {
	private LibraryModel library = new LibraryModel();
	
	private Artist artist = new Artist("Pink Floyd");
	private Album album = new Album("Animals", "Rock", "1977", "Pink Floyd");
	private Song song1 = new Song("Dogs", "Animals", "Pink Floyd");
	private Song song2 = new Song("Pigs", "Animals", "Pink Floyd");
	
	private Playlist playlist1 = new Playlist("Day");
	private Playlist playlist2 = new Playlist("Night");
	
	
    @Test
    public void testCopyConstructor() {
    	LibraryModel copy = new LibraryModel(library);
    	
        copy.addSong(song1);
        copy.addPlaylist("Workout Mix");
        copy.addAlbum(album);
        copy.addArtist(artist);

        assertEquals(0, library.getLibrarySongs().size());
        assertEquals(0, library.getLibraryPlaylists().size());
        assertEquals(0, library.getLibraryAlbums().size());
        assertEquals(0, library.getLibraryArtists().size());
        assertEquals(1, copy.getLibrarySongs().size());
        assertEquals(1, copy.getLibraryPlaylists().size());
        assertEquals(1, copy.getLibraryAlbums().size());
        assertEquals(1, copy.getLibraryArtists().size());
    }
	
	@Test
	public void testAddSong() {
		LibraryModel copy = new LibraryModel(library);
		assertEquals(0, copy.getLibrarySongs().size());
		copy.addSong(song1);
		copy.addSong(song2);
		assertEquals(2, copy.getLibrarySongs().size());
		
		// testing duplicate song
		copy.addSong(song1);
		assertEquals(2, copy.getLibrarySongs().size());
	}
	

    @Test
    public void testAddToFavorites() {
    	LibraryModel copy = new LibraryModel(library);
    	copy.addSong(song1);
    	copy.addSong(song2);
        copy.addToFavorites(song1);
        
        assertEquals(2, copy.getLibrarySongs().size());
        assertEquals(1, copy.getLibraryFavorites().size());
        
        // testing duplicate song
        copy.addToFavorites(song1);
        assertEquals(1, copy.getLibraryFavorites().size());
    }
    
    @Test
    public void testAddPlaylist() {
    	LibraryModel copy = new LibraryModel(library);
    	assertEquals(0, copy.getLibraryPlaylists().size());
        copy.addPlaylist("Chill");
        copy.addPlaylist("Workout");

        assertEquals(2, copy.getLibraryPlaylists().size());
        
        // testing duplicate playlist
        copy.addPlaylist("Chill");
        assertEquals(2, copy.getLibraryPlaylists().size());
    }
    
    @Test
    public void testAddSongToPlaylist() {
    	LibraryModel copy = new LibraryModel(library);
        copy.addPlaylist("Chill");
        assertEquals(copy.getLibraryPlaylists().size(), 1);
        Playlist playlist = copy.getLibraryPlaylists().get(0);
        
        
        assertEquals(0, playlist.getPlaylistSongs().size());
        copy.addSongToPlaylist(song1, playlist);
        assertEquals(1, playlist.getPlaylistSongs().size());
        assertEquals("Dogs", playlist.getPlaylistSongs().get(0).getSongName());
        
        // adding duplicate song to playlist
        copy.addSongToPlaylist(song1, playlist);
        assertEquals(1, playlist.getPlaylistSongs().size());
    }
    
    @Test
    public void testRemoveSongFromPlaylist() {
    	LibraryModel copy = new LibraryModel(library);
        copy.addPlaylist("Chill Vibes");
        Playlist playlist = copy.getLibraryPlaylists().get(0);

        library.addSongToPlaylist(song1, playlist);
        library.removeSongFromPlaylist(song1, playlist);

        assertEquals(0, playlist.getPlaylistSongs().size());
    }
    
    @Test
    public void testAddAlbum() {
    	LibraryModel copy = new LibraryModel(library);
    	assertEquals(0, copy.getLibraryAlbums().size());
        copy.addAlbum(album);

        assertEquals(1, copy.getLibraryAlbums().size());
        assertEquals("Animals", copy.getLibraryAlbums().get(0).getAlbumName());
        
        //testing duplicate albums
        copy.addAlbum(album);
        assertEquals(1, copy.getLibraryAlbums().size());
    }
    
    @Test
    public void testAddArtist() {
    	LibraryModel copy = new LibraryModel(library);
        copy.addArtist(artist);

        assertEquals(1, copy.getLibraryArtists().size());
        assertEquals("Pink Floyd", copy.getLibraryArtists().get(0).getArtistName());
        
        // duplicate artist
        copy.addArtist(artist);
        assertEquals(1, copy.getLibraryArtists().size());
    }


}


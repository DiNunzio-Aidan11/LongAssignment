package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import model.Album;
import model.Artist;
import model.Song;

public class TestArtist {
	private Artist artist = new Artist("Pink Floyd");
	
	@Test
	public void testConstructor() {
        assertEquals("Pink Floyd", artist.getArtistName());
        assertTrue(artist.getAllAlbums().isEmpty());
	}
	
	@Test
	public void testCopyConstructor() {
		Artist copy = new Artist(artist);
		assertEquals(copy.getArtistName(), "Pink Floyd");
		copy.addAlbum("Animals", "Rock", "1977");
		ArrayList<Album> albums = artist.getAllAlbums();
		ArrayList<Album> copyAlbums = copy.getAllAlbums();
		
		assertEquals(albums.size(), 0);
		assertEquals(copyAlbums.size(), 1);
		
	}
	
    @Test
    public void testGetAllAlbums() {
    	Artist copy = new Artist(artist);
    	copy.addAlbum("Animals", "Rock", "1977");
    	copy.addAlbum("Dark Side of the Moon", "Rock", "1973");

        ArrayList<Album> albums = copy.getAllAlbums();
        assertEquals(2, albums.size());
        assertEquals("1977", albums.get(0).getAlbumYear());
        assertEquals("1973", albums.get(1).getAlbumYear());

        // for escaping references
        albums.get(0).addSong("Dogs");
        ArrayList<Album> secondCopy = copy.getAllAlbums();
        ArrayList<Song> songs = secondCopy.get(0).getAllSongs();
        assertEquals(0, songs.size());
        
    }
	
	@Test
	public void testAddAlbum() {
		Artist copy = new Artist(artist);
		ArrayList<Album> albums = copy.getAllAlbums();
		assertEquals(albums.size(), 0);
		
		copy.addAlbum("Animals", "Rock", "1977");
		
		albums = copy.getAllAlbums();
		assertEquals(albums.size(), 1);
		
		// duplicate album
		copy.addAlbum("Animals", "Rock", "1977");
		albums = copy.getAllAlbums();
		assertEquals(albums.size(), 1);
	}
	
	@Test
	public void testGetCertainAlbum() {
		Artist copy = new Artist(artist);
		copy.addAlbum("Animals", "Rock", "1977");
		Album album = copy.getCertainAlbum("Animals");
		assertEquals(album.getAlbumGenre(), "Rock");
		assertEquals(album.getAlbumYear(), "1977");
		assertEquals(album.getAlbumName(), "Animals");
		
		// on album that the artist does not have
		assertNull(copy.getCertainAlbum("Dark Side of the Moon"));
		
	}

}


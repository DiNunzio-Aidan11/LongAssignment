package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import model.Album;
import model.Song;

public class TestAlbum {
	private Album album = new Album("Animals", "Rock", "1977", "Pink Floyd");
	
	@Test
	public void testConstructor() {
        assertEquals("Animals", album.getAlbumName());
        assertEquals("Rock", album.getAlbumGenre());
        assertEquals("1977", album.getAlbumYear());
        assertEquals("Pink Floyd", album.getArtistName());
        assertTrue(album.getAllSongs().isEmpty());
	}
	
	@Test
	public void testCopyConstructor() {
		// testing a copy with no songs
		Album copy = new Album(album);
        assertEquals(album.getAlbumName(), copy.getAlbumName());
        assertEquals(album.getAlbumGenre(), copy.getAlbumGenre());
        assertEquals(album.getAlbumYear(), copy.getAlbumYear());
        assertEquals(album.getArtistName(), copy.getArtistName());
		copy.addSong("Dogs");
		copy.addSong("Pigs");
		
		assertTrue(album.getAllSongs().isEmpty());
		assertFalse(copy.getAllSongs().isEmpty());
		
		// escaping references 
		Album doubleCopy = new Album(copy);
        assertEquals(doubleCopy.getAlbumName(), copy.getAlbumName());
        assertEquals(doubleCopy.getAlbumGenre(), copy.getAlbumGenre());
        assertEquals(doubleCopy.getAlbumYear(), copy.getAlbumYear());
        assertEquals(doubleCopy.getArtistName(), copy.getArtistName());
        
        ArrayList<Song> copySongs = copy.getAllSongs();
        ArrayList<Song> doubleCopySongs = doubleCopy.getAllSongs();
        assertFalse(doubleCopy.getAllSongs().isEmpty());
        assertEquals(copySongs.size(), doubleCopySongs.size());
	}
	
	@Test
	public void testGetAllSongs() {
		Album copy = new Album(album);
		copy.addSong("Dogs");
		copy.addSong("Pigs");
		
        ArrayList<Song> songs = copy.getAllSongs();
        assertEquals(2, songs.size());
		assertEquals("Dogs", songs.get(0).getSongName());
		assertEquals("Pigs", songs.get(1).getSongName());
		
		// modify wont hurt original
		ArrayList<Song> copyAgain = copy.getAllSongs();
		songs.get(0).setFavorite(true);
		assertNotEquals(songs.get(0).isFavorite(), copyAgain.get(0).isFavorite());
        


	}
	
	@Test 
	public void testAddSong() {
		Album copy = new Album(album);
		copy.addSong("Dogs");
		copy.addSong("Pigs");
		
		ArrayList<Song> songs = copy.getAllSongs();
		assertEquals(2, songs.size());
		assertEquals("Dogs", songs.get(0).getSongName());
		assertEquals("Pigs", songs.get(1).getSongName());
	}
	
	@Test
	public void testAddDuplicateSong() {
		Album copy = new Album(album);
		copy.addSong("Dogs");
		copy.addSong("Dogs");
		
		ArrayList<Song> songs = copy.getAllSongs();
		assertEquals(1, songs.size());
	}
	
	@Test
	public void getCertainSong() {
		Album copy = new Album(album);
		copy.addSong("Dogs");
		
		assertNull(copy.getCertainSong("Pigs"));
		
		assertNotNull(copy.getCertainSong("Dogs"));
		
		
	}

}


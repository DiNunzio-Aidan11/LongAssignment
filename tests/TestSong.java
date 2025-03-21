package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Rating;
import model.Song;

public class TestSong {
	private Song song = new Song("Dogs", "Animals", "Pink Floyd");
	
	@Test
	public void testConstructor() {
		assertEquals("Dogs", song.getSongName());
		assertEquals("Animals", song.getAlbumName());
		assertEquals("Pink Floyd", song.getArtistName());
        assertFalse(song.isFavorite());
        assertNull(song.getRating());
	}
	
	@Test
	public void testCopyConstructor() {
		Song copy = new Song(song);
		assertEquals("Dogs", copy.getSongName());
		assertEquals("Animals", copy.getAlbumName());
		assertEquals("Pink Floyd", copy.getArtistName());
		copy.setFavorite(true);
		copy.setRating(Rating.FIVESTAR);
        assertFalse(song.isFavorite());
        assertNull(song.getRating());        
        
	}
	
    @Test
    public void testSetFavorite() {
    	Song copy = new Song(song);
        copy.setFavorite(true);
        assertTrue(copy.isFavorite());
        copy.setFavorite(false);
        assertFalse(copy.isFavorite());
    }
    
    @Test
    public void testSetRating() {
    	Song copy = new Song(song);
        copy.setRating(Rating.FOURSTAR);
        assertEquals(Rating.FOURSTAR, copy.getRating());

        copy.setRating(Rating.TWOSTAR);
        assertEquals(Rating.TWOSTAR, copy.getRating());
    }
    
    
	

}


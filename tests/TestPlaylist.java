package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import model.Playlist;
import model.Rating;
import model.Song;

public class TestPlaylist {
	private Playlist playlist = new Playlist("Top Rock");
	private Song song1 = new Song("Dogs", "Animals", "Pink Floyd");
	private Song song2 = new Song("Pigs", "Animals", "Pink Floyd");
	
	@Test
	public void testConstructor() {
		assertEquals(playlist.getPlaylistName(), "Top Rock");
        assertTrue(playlist.getPlaylistSongs().isEmpty());

	}
	
	@Test
	public void testCopyConstructor() {
		Playlist copy = new Playlist(playlist);
		assertEquals(copy.getPlaylistName(), "Top Rock");
		assertTrue(copy.getPlaylistSongs().isEmpty());
		
		// copying with songs
		copy.addSong(song1);
		copy.addSong(song2);
		assertFalse(copy.getPlaylistSongs().isEmpty());
		
		Playlist secondCopy = new Playlist(copy);
		assertEquals(secondCopy.getPlaylistName(), "Top Rock");
		assertFalse(secondCopy.getPlaylistSongs().isEmpty());
		
	}
	
	@Test
	public void testAddSong() {
		Playlist copy = new Playlist(playlist);
        copy.addSong(song1);
        copy.addSong(song2);

        ArrayList<Song> songs = copy.getPlaylistSongs();
        assertEquals(2, songs.size());
        assertEquals("Dogs", songs.get(0).getSongName());
        assertEquals("Pigs", songs.get(1).getSongName());
		
	}
	
	@Test
	public void testRemoveSong() {
		Playlist copy = new Playlist(playlist);
        copy.addSong(song1);
        copy.addSong(song2);
        ArrayList<Song> songs = copy.getPlaylistSongs();
        assertEquals(2, songs.size());

        copy.removeSong(song1);
        songs = copy.getPlaylistSongs();
        assertEquals(1, songs.size());
        
        assertEquals("Pigs", songs.get(0).getSongName());

        copy.removeSong(song2);
        assertTrue(copy.getPlaylistSongs().isEmpty());
	}
	
	@Test
	public void testGetPlaylistSongs() {
		Playlist copy = new Playlist(playlist);
		copy.addSong(song1);
		copy.addSong(song2);
		
		Playlist secondCopy = new Playlist(copy);
		
		ArrayList<Song> songs = copy.getPlaylistSongs();
		assertEquals(songs.get(0).getSongName(), "Dogs");
		assertEquals(songs.get(1).getSongName(), "Pigs");
		
		// escaping reference
		songs.get(0).setFavorite(true);
		songs.get(1).setRating(Rating.FOURSTAR);
		
		ArrayList<Song> songsSecondCopy = secondCopy.getPlaylistSongs();
		assertNotEquals(songs.get(0).isFavorite(), songsSecondCopy.get(0).isFavorite());
		assertNotEquals(songs.get(1).getRating(), songsSecondCopy.get(1).getRating());
		
		
	}

}


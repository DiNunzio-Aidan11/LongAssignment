package model;
import java.util.ArrayList;
/*
 * Class Artist 
 * This class stores the name of the artist object and an array list of album objects
 */


public class Artist {
	private ArrayList<Album> albums = new ArrayList<>();
	private final String name;
	
	// @pre Name != ""
	public Artist(String Name) {
		this.name = Name;
	}
	
	public void addAlbum(String albumName, String albumGenre, String albumYear ) {
		/*
		 * This will add an album object to the artist and add the year, genre, and name of the album
		 */
		int seen = 0;
		for (Album album : this.albums) {
			if (album.getAlbumName() == albumName) {
				seen = 1;
				break;
			}
		}
		if (seen == 1) {
			System.out.println("Error Duplicate Album: " + albumName + " for Artist: " + this.name);
		}
		else {
			albums.add(new Album(albumName, albumGenre, albumYear, this.name));
		}
	}
	public Album getCertainAlbum(String albumName) {
		/*
		 * checks to see if there is an album and returns copy of it if found otherwise null
		 * used for an album by title
		 */
		for (Album album : this.albums) {
			if (album.getAlbumName() == albumName) {
				return new Album(album); // returns a copy of the album
			}
		}
		return null;
	}
	
	public ArrayList<Album> getAllAlbums() { 
		/*
		 * returns a copy of all the album objects that a artist has, empty list if none found
		 */
		ArrayList<Album> copy = new ArrayList<>();
		for (Album album : albums) {
			copy.add(new Album(album));
			
		}
		return copy;
	}
    
    public String getArtistName() {
        return this.name;
    }
}

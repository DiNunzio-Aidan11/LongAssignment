package model;
import java.util.ArrayList;

public class Artist {
	private ArrayList<Album> albums = new ArrayList<>();
	private final String name;
	
	// @pre firstName != "", lastName != ""
	public Artist(String Name) {
		this.name = Name;
	}
	public void addAlbum(String albumName, String albumGenre, String albumYear ) {
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
			albums.add(new Album(albumName, albumGenre, albumYear));
		}
	}
	public Album getCertainAlbum(String albumName) { // used for an album by title
		for (Album album : this.albums) {
			if (album.getAlbumName() == albumName) {
				return album;
			}
		}
		System.out.println("Album not Found " + albumName + " for Artist: " + this.name);
		return null;
	}
	
	public ArrayList<Album> getAllAlbums() { // used for getting album by artist
		return this.albums;
	}
    
    public String getArtistName() {
        return this.name;
    }
}

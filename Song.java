package model;

/*
 * Class Song 
 * This class stores the name of the song as well as if it's a favorite and the rating of a song
 */

public class Song {
	private final String songName;
	private final String artistName;
	private final String albumName;
	private boolean favorite;
	private Rating rating;
	
	// main constructor
	// @pre songName != ""
	public Song(String songName, String albumName, String artistName) {
		this.songName = songName;
		this.albumName = albumName;
		this.artistName = artistName;
	}
	
	// copy constructor
	public Song(Song other) {
		this.songName = other.songName;
		this.favorite = other.favorite;
		this.rating = other.rating;
		this.artistName = other.artistName;
		this.albumName = other.albumName;
		}
	
	public String getSongName() {
		return this.songName;
	}
	
	public void setRating(Rating rating) {
		/*
		 * sets a rating of one star to five star
		 */
		this.rating = rating;
	}
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	public Rating getRating() {
		return this.rating;
	}
	
	public boolean isFavorite() {
		// returns if a song is considered favorite or not
		return this.favorite;
	}
	
	public String getArtistName() {
		return this.artistName;
	}
	
	public String getAlbumName() {
		return this.albumName;
	}
	
	@Override
	public boolean equals(Object other) {
	    if (other == null || getClass() != other.getClass()) {
	        return false; // Null or different class -> not equal
	    }

	    Song song = (Song) other;
		return this.getArtistName().equals(song.getArtistName()) && this.getSongName().equals(song.getSongName()) && 
				this.getAlbumName().equals(song.getAlbumName());
	}

}
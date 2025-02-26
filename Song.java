package model;

public class Song {
	private final String songName;
	private boolean favorite;
	private Rating rating;
	
	// main constructor
	// @pre songName != ""
	public Song(String songName) {
		this.songName = songName;
	}
	
	// copy constructor
	public Song(Song other) {
		this.songName = other.songName;
		this.favorite = other.favorite;
		this.rating = other.rating;
		}
	
	public String getSongName() {
		return this.songName;
	}
	
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	public Rating getRating() {
		return this.rating;
	}
	
	public boolean isFavorite() {
		return this.favorite;
	}

}
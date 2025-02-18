package model;

public class Song {
	private String songName;
	private boolean favorite;
	private int rating;
	
	public Song(String songName) {
		this.songName = songName;
	}
	
	public String getSongName() {
		return this.songName;
	}
	
	public void setRating(int rating) {
		if (rating < 0 || rating > 5) {
			System.out.println("invalid rating: " + rating);
		}
		else {
			this.rating = rating;
		}
	}
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public boolean isFavorite() {
		return this.favorite;
	}

}


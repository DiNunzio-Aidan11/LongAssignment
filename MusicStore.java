package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Class MusicStore 
 * This class parses album information and processes it into an array list
 * that store Albums of that artists which albums store songs 
 */

public class MusicStore {
	public static int getArtistLocation(ArrayList<Artist> artists, String artistName) {
		/*
		 * used for find the index of an artist or -1 if not found
		 */
		for (int i = 0; i < artists.size(); i++) {
			if (artists.get(i).getArtistName().equals(artistName)) {
				return i;

			}
		}
		return -1;
	}
	public static ArrayList<Artist> parseAlbumTextFile(String fileName) {
		/*
		 * Parses a text file eg. album.txt to store the information into the correct array list
		 */
	    ArrayList<Artist> artists = new ArrayList<>();
	    
	    try {
	        File file = new File(fileName);
	        Scanner scanner = new Scanner(file);  // Using Scanner to read the file
	        
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine().strip();
	            String[] albumAndName = line.split(",");

	            if (albumAndName.length < 2) {
	                System.out.println("Skipping invalid line: " + line);
	                continue;
	            }

	            String albumName = albumAndName[0];
	            String artistName = albumAndName[1];

	            int artistLocation = getArtistLocation(artists, artistName);
	            Artist artist;

	            if (artistLocation != -1) {
	                artist = artists.get(artistLocation);
	            } else {
	                artist = new Artist(artistName);
	                artists.add(artist);
	            }

	            artist.addAlbum(albumName, "Unknown Genre", "Unknown Year"); // Placeholder values

	        }
	        scanner.close();
	    } catch (FileNotFoundException e) {
	        System.err.println("Error: File not found - " + fileName);
	    }

	    return artists;
	}


}

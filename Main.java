import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import model.Artist;
import model.MusicStore;
import view.UI;

public class Main {
	/*
	 * First parses in the information to the store then calls for user inputs to process 
	 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Album Text File: ");
        String fileName = scanner.nextLine();
        scanner.close();

        File file = new File(fileName); // Create a File object
        
        if (!file.exists()) {  // Check if file exists before proceeding
            System.out.println("Error: File not found!");
            return;
        }

        ArrayList<Artist> store = MusicStore.parseAlbumTextFile(fileName);
        
        // lets the view class have a reference to the stores information 
        UI userInterface = new UI(store);
        for (Artist artist : store) {
            System.out.println(artist.getArtistName());
        }
    }
}

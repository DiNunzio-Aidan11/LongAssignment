import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import model.Artist;
import model.MusicStore;
import view.UI;

public class Main {
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

        ArrayList<Artist> artists = MusicStore.parseAlbumTextFile(fileName);
        UI userInterface = new UI(artists);

        for (Artist art : artists) {
            System.out.println(art.getArtistName());
        }
    }
}

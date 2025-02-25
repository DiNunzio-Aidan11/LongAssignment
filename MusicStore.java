package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class MusicStore {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Album Text File");
        String fileName = scanner.nextLine();
        scanner.close();
        ArrayList<Artist> artists = parseAlbumTextFile(fileName);
        System.out.println(artists);
	}

    public static int getArtistLocation(ArrayList<Artist> artists, String artistName) {
        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getArtistName().equals(artistName)) {
                return i;

            }
        }
        return -1;
    }

    public static ArrayList<Artist> parseAlbumTextFile(String fileName) {

        ArrayList<Artist> artists = new ArrayList<>();

        try {
            FileReader file = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(file);

            String line = buffer.readLine();

            while (line != null) {
                String[] albumAndName = line.strip().split(",");

                String artistName = albumAndName[1];
                String albumName = albumAndName[0];

                int artistLocation = getArtistLocation(artists, artistName);
                Artist artist;
                if (artistLocation != -1) {
                    artist = artists.get(artistLocation);
                }
                else {
                    artist = new Artist(artistName);
                    artists.add(artist);
                }
                
                String albumFileName = String.format("%s_%s.txt", albumName, artistName);
                
                try {
                    FileReader albumFile = new FileReader(albumFileName);
                    BufferedReader albumBuffer = new BufferedReader(albumFile);

                    String albumLine = albumBuffer.readLine();
                    boolean firstLineReached = false;

                    while (albumLine != null) {
                        if (firstLineReached == false) {
                            String[] genreAndYear = albumLine.strip().split(",");

                            String albumGenre = genreAndYear[2];
                            String albumYear = genreAndYear[3];

                            artist.addAlbum(albumName, albumGenre, albumYear);
                            firstLineReached = true;
                        }
                        else {
                            albumLine = albumLine.strip();
                            artist.getCertainAlbum(albumName).addSong(albumLine);
                        }
                        albumLine = albumBuffer.readLine();
                    }
                    albumBuffer.close();
                }
                catch (IOException e){
                    System.err.println("Error: File is not able to be parsed " + e.getMessage());
                }

                line = buffer.readLine();
            }
            buffer.close();
        }

        catch (IOException e) {
            System.err.println("Error: File is not able to be parsed " + e.getMessage());
        }
        return artists;
    }

}


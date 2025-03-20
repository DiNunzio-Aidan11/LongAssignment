import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.String;

import model.Album;
import model.Artist;
import model.MusicStore;
import model.Playlist;
import model.Password;
import model.Rating;
import model.Song;
import model.User;
import model.Users;
import view.UI;

public class Main {
	/*
	 * First parses in the information to the store then calls for user inputs to process 
	 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter Album Text File: ");
        //String fileName = scanner.nextLine();

        File file = new File("albums.txt"); 
        
        if (!file.exists()) {  // Check if file exists before proceeding
            System.out.println("Error: File not found!");
            return;
        }

        ArrayList<Artist> store = MusicStore.parseAlbumTextFile("albums.txt");
        
        // lets the view class have a reference to the stores information 
        UI userInterface = new UI(store);
        
        boolean storeMode = true;
        boolean loggedIn = false;
        
        
        //TODO: add in stored information for users from other file
        Users users = new Users();
        User curUser;
        while (true) {
        	while (!loggedIn) {
                System.out.println("Welcome! Please Choose an Option:");
                System.out.println("Input '1': Log in");
                System.out.println("Input '2': Register User");
                System.out.println("Input '3': To Quit");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    if (users.userExists(username)) {
                    	System.out.print("Enter password: ");
                    	String password = scanner.nextLine();
                    	password = Password.encrypt(password);
                    
                    	if (users.authenticate(username, password)) {
                    		System.out.println("Login successful! Welcome, " + username);
                    		loggedIn = true;
                    		curUser = users.getUser(username);
                    		userInterface.setUser(curUser);
                    	} 
                    	else {
                    		System.out.println("Invalid credentials, try again.");
                    	}
                    }
                    else {
                    	System.out.print("User does not exist");
                    }
                } 
                else if (choice.equals("2")) {
                	String username = "";
                	boolean uniqueUser = false;
                	while (!uniqueUser) {
                		System.out.print("Choose a username: ");
                		username = scanner.nextLine();
                		if (username.length() == 0) {
                			System.out.println("Cant have blank username");
                		}
                		else if (users.userExists(username)) {
                			System.out.println("username already taken");
                		}
                		else {
                			uniqueUser = true;
                		}
                	}
                    System.out.print("Choose a password: ");
                    String password = scanner.nextLine();
                    password = Password.encrypt(password);
                    
                    if (users.addUser(username, password)) {
                        System.out.println("You can now log in.");
                    }
                }
                else if (choice.equals("3")) {
                	break;
                }
                else {
                    System.out.println("Invalid option, try again.");
                }
            }
        	if (storeMode == true) {
	        	System.out.println("========== store ===========");
	        	System.out.println("Input 'a': search for songs by title");
	        	System.out.println("Input 'b': search for songs by artist");
	        	System.out.println("Input 'c': search for album by title");
	        	System.out.println("Input 'd': search for album by artist");
	        	System.out.println("Input 'e': switch to library");
	        	System.out.println("Input 'logout': to logout");
	        	System.out.println();
	        	
	        	String input = scanner.nextLine();
	        	if (input.equals("a")) {
	        		System.out.println("Enter song title");
	        		input = scanner.nextLine();
	        		ArrayList<Song> foundSong = userInterface.searchByTitle(input);
	        		if (foundSong.size() == 0) {
	        			System.out.println("Song does not exist in store");
	        		}
	        		else {
	        			for (Song song : foundSong) {
							System.out.println(song.getSongName() + ", " + song.getArtistName() + ", " + song.getAlbumName());
	        			}
	        		}
	        	}
	        	else if (input.equals("b")) {
	        		System.out.println("Enter artist");
	        		input = scanner.nextLine();
	        		ArrayList<Song> foundSongs = userInterface.searchByArtist(input);
	        		if (foundSongs.size() == 0) {
	        			System.out.print("Artist does not have any songs in store");
	        		}
	        		else {
	        			for (Song foundSong : foundSongs) {
	        				System.out.println(foundSong.getSongName() + ", " + foundSong.getArtistName() + ", " + foundSong.getAlbumName());
	        			}
	        		}
	        	}
	        	else if (input.equals("c")) {
	        		System.out.println("Enter album title");
	        		input = scanner.nextLine();
	        		ArrayList<Album> foundAlbums = userInterface.searchByAlbumTitle(input);
	        		if (foundAlbums.size() == 0) {
	        			System.out.println("Album does not exist in store");
	        		}
	        		else {
		        		for (Album album : foundAlbums) {
		    				System.out.println("Album: " + album.getAlbumName());
		    				System.out.println("Artist: " + album.getArtistName());
		    				System.out.println("Released: " + album.getAlbumYear());
		    				System.out.println("Genre: " + album.getAlbumGenre());
		    				System.out.println("Songs:");
		    				for (Song song : album.getAllSongs()) {
		    					System.out.println("\t" + song.getSongName());
		    				}
		        		}
		        	}
	        	}
	        	else if (input.equals("d")) {
	        		System.out.println("Enter album artist");
	        		input = scanner.nextLine();
	        		ArrayList<Album> foundAlbums = userInterface.searchByAlbumArtist(input);
	        		if (foundAlbums.size() == 0) {
	        			System.out.println("Artist does not have existing albums in store");
	        		}
	        		else {
		        		for (Album album : foundAlbums) {
		    				System.out.println("Album: " + album.getAlbumName());
		    				System.out.println("Artist: " + album.getArtistName());
		    				System.out.println("Released: " + album.getAlbumYear());
		    				System.out.println("Genre: " + album.getAlbumGenre());
		    				System.out.println("Songs:");
		    				for (Song song : album.getAllSongs()) {
		    					System.out.println("\t" + song.getSongName());
		    				}
		        		}
	        		}
	        	}
	        	else if (input.equals("e")) {
	        		storeMode = false;
	        	}
	        	else if (input.equals("logout")) {
	        		loggedIn = false;
	        	}
	        	else {
	        		System.out.println("Invalid input selected: try again");
	        		continue;
	        	}
        	}
        	else {
        		System.out.println("========== library ==========");
        		System.out.println("Input 'a': add playlist");
        		System.out.println("Input 'b': add song to library");
        		System.out.println("Input 'c': add album to library");
        		System.out.println("Input 'd': list artists");
        		System.out.println("Input 'e': list albums");
        		System.out.println("Input 'f': list playlists");
        		System.out.println("Input 'g': list favorites");
        		System.out.println("Input 'h': rate song");
        		System.out.println("Input 'i: select playlist");
        		System.out.println("Input 'j': delete song from playlist");
        		System.out.println("Input 'k': add song to playlist");
        		System.out.println("Input l: list songs in library");
	        	System.out.println("Input 'm': search for songs by title");
	        	System.out.println("Input 'n': search for songs by artist");
	        	System.out.println("Input 'o': search for album by title");
	        	System.out.println("Input 'p': search for album by artist");
        		System.out.println("Input 'q': switch to store");
        		System.out.println("Input 'logout': to logout");
        		
        		String input = scanner.nextLine();
        		if (input.equals("a")) {
        			System.out.println("Enter playlist name");
        			input = scanner.nextLine();
        			userInterface.createPlaylist(input);
        			String format = String.format("Playlist %s added to library", input);
        			System.out.println(format);
        		}
        		else if (input.equals("b")) {
        			System.out.println("Input song to add to library");
        			input = scanner.nextLine();
	        		ArrayList<Song> foundSongs = userInterface.searchByTitle(input);
	        		if (foundSongs.size() == 0) {
	        			System.out.println("Song does not exist in store");
	        		}
        			else if (foundSongs.size() > 1) {
        				boolean albumSelected = false;
        				while (albumSelected == false) {
	        				System.out.println("Enter artist of desired song");
	        				for (Song song : foundSongs) {
	        					System.out.println(song.getArtistName());
	        				}
	        				input = scanner.nextLine();
	        				for (Song song1 : foundSongs) {
	        					if (song1.getArtistName().equals(input)) {
	        						userInterface.addSongToLibrary(song1);
	        						String format = String.format("%s added to library", song1.getSongName());
	        						System.out.println(format);
	        						albumSelected = true;
	        						Artist songArtist = userInterface.searchArtistFromSong(song1.getArtistName());
	        						if (!userInterface.getLibraryArtists().contains(songArtist) && songArtist != null) {
	        							userInterface.addArtistToLibrary(songArtist);
	        							albumSelected = true;
	        						}
	        					}
	        				}
        				}
        			}
        			else {
        				Song ourSong = foundSongs.get(0);
        				userInterface.addSongToLibrary(ourSong);
						Artist songArtist = userInterface.searchArtistFromAlbum(ourSong.getArtistName());
						if (!userInterface.getLibraryArtists().contains(songArtist) && songArtist != null) {
							userInterface.addArtistToLibrary(songArtist);
						}
        				String format = String.format("%s added to library", ourSong.getSongName());
        				System.out.println(format);
        			}
        		}
        		else if (input.equals("c")) {
        			System.out.println("Input album to add to library");
        			input = scanner.nextLine();
        			ArrayList<Album> foundAlbums = userInterface.searchByAlbumTitle(input);
        			if (foundAlbums.size() == 0) {
        				System.out.println("Album does not exist in store");
        			}
        			else if (foundAlbums.size() > 1) {
        				boolean albumSelected = false;
        				while (albumSelected == false) {
	        				System.out.println("Enter artist of desired album");
	        				for (Album foundAlbum : foundAlbums) {
	        					System.out.println(foundAlbum.getArtistName());
	        				}
	        				input = scanner.nextLine();
	        				for (Album foundAlbum1 : foundAlbums) {
	        					if (foundAlbum1.getArtistName().equals(input)) {
	        						userInterface.addAlbumToLibrary(foundAlbum1);
	                				String format = String.format("%s added to library", foundAlbum1.getAlbumName());
	                				System.out.println(format);
	        						albumSelected = true;
	        						Artist songArtist = userInterface.searchArtistFromAlbum(foundAlbum1.getArtistName());
	        						if (!userInterface.getLibraryArtists().contains(songArtist) && songArtist != null) {
	        							userInterface.addArtistToLibrary(songArtist);
	        						}
	        					}
	        				}
	        			}
        			}
        			else {
        				Album ourAlbum = foundAlbums.get(0);
        				userInterface.addAlbumToLibrary(ourAlbum);
						Artist songArtist = userInterface.searchArtistFromAlbum(ourAlbum.getArtistName());
						if (!userInterface.getLibraryArtists().contains(songArtist) && songArtist != null) {
							userInterface.addArtistToLibrary(songArtist);
						}
        				String format = String.format("%s added to library", ourAlbum.getAlbumName());
        				System.out.println(format);
        			}
        		}
        		else if (input.equals("d")) {
        			for (Artist artist : userInterface.getLibraryArtists()) {
        				System.out.println(artist.getArtistName());
        			}
        		}
        		else if (input.equals("e")) {
        			for (Album album : userInterface.getLibraryAlbums()) {
        				System.out.println(album.getAlbumName());
        			}
        		}
        		else if (input.equals("f")) {
        			for (Playlist playlist : userInterface.getLibraryPlaylists()) {
        				System.out.println(playlist.getPlaylistName());
        			}
        		}
        		else if (input.equals("g")) {
        			for (Song song : userInterface.getLibraryFavorites()) {
        				System.out.println(song.getSongName());
        			}
        		}
        		else if (input.equals("h")) {
        			System.out.println("Select song to rate");
        			input = scanner.nextLine();
        			ArrayList<Song> foundSongs = userInterface.getLibrarySongs();
        			if (foundSongs.size() == 0) {
        				System.out.println("Song is not in your library");
        			}
        			else if (foundSongs.size() > 1) {
        				boolean songSelected = false;
        				while (songSelected == false) {
	        				System.out.println("Enter artist of desired song");
	        				for (Song song : foundSongs) {
	        					System.out.println(song.getArtistName());
	        				}
	        				input = scanner.nextLine();
	        				for (Song song1: foundSongs) {
	        					if (song1.getArtistName().equals(input)) {
	        						songSelected = true;
	        						Song ourSong = song1;
	        						boolean correctRate = false;
	        						while (correctRate == false) {
		                				System.out.println("Insert rating: must be int 1-5");
		                				input = scanner.nextLine();
		                				if (input.equals("1")) {
		                					userInterface.rateSong(ourSong, Rating.ONESTAR);
		                					correctRate = true;
		                				}
		                				else if (input.equals("2")) {
		                					userInterface.rateSong(ourSong, Rating.TWOSTAR);
		                					correctRate = true;
		                				}
		                				else if (input.equals("3")) {
		                					userInterface.rateSong(ourSong, Rating.THREESTAR);
		                					correctRate = true;
		                				}
		                				else if (input.equals("4")) {
		                					userInterface.rateSong(ourSong, Rating.FOURSTAR);
		                					correctRate = true;
		                				}
		                				else if (input.equals("5")) {
		                					userInterface.rateSong(ourSong, Rating.FIVESTAR);
		                					correctRate = true;
		                				}
		                				else {
		                					System.out.println("Invalid rating");
		                					continue;
		                				}
	        						}
	        					}
	        				}
        				}
        			}
        			else {
        				Song ourSong = foundSongs.get(0);
						boolean correctRate = false;
						while (correctRate == false) {
            				System.out.println("Insert rating: must be int 1-5");
            				input = scanner.nextLine();
            				if (input.equals("1")) {
            					userInterface.rateSong(ourSong, Rating.ONESTAR);
            					correctRate = true;
            				}
            				else if (input.equals("2")) {
            					userInterface.rateSong(ourSong, Rating.TWOSTAR);
            					correctRate = true;
            				}
            				else if (input.equals("3")) {
            					userInterface.rateSong(ourSong, Rating.THREESTAR);
            					correctRate = true;
            				}
            				else if (input.equals("4")) {
            					userInterface.rateSong(ourSong, Rating.FOURSTAR);
            					correctRate = true;
            				}
            				else if (input.equals("5")) {
            					userInterface.rateSong(ourSong, Rating.FIVESTAR);
            					correctRate = true;
            				}
            				else {
            					System.out.println("Invalid rating");
            					continue;
            				}
						}
        			}
        		}
        			

        		else if (input.equals("i")) {
        			System.out.println("Enter playlist you wish to view");
        			input = scanner.nextLine();
        			for (Playlist playlist : userInterface.getLibraryPlaylists()) {
        				if (playlist.getPlaylistName().equals(input)) {
        					for (Song song : playlist.getPlaylistSongs()) {
        						System.out.println(song.getSongName() + ", " + song.getArtistName());
        					}
        				}
        			}
        		}
        		else if (input.equals("j")) {
        			System.out.println("Enter playlist you wish to remove song from");
        			input = scanner.nextLine();
        			for (Playlist playlist : userInterface.getLibraryPlaylists()) {
        				if (playlist.getPlaylistName().equals(input)) {
        					System.out.println("Enter song you wish to remove from playlist");
        					input = scanner.nextLine();
        					for (Song song : playlist.getPlaylistSongs()) {
        						if (song.getSongName().equals(input)) {
        							userInterface.removeFromPlaylist(song, playlist);
                    				String format = String.format("%s removed from %s", song.getSongName(), playlist.getPlaylistName());
                    				System.out.println(format);
        						}
        					}
        				}
        			}
        			System.out.print("Song or Playlist not in library");
        		}
        		else if (input.equals("k")) {
        			System.out.println("Enter playlist you with to add song to");
        			input = scanner.nextLine();
        			for (Playlist playlist : userInterface.getLibraryPlaylists()) {
        				if (playlist.getPlaylistName().equals(input)) {
        					System.out.println("Enter song you wish to add to playlist");
        					input = scanner.nextLine();
        					ArrayList<Song> foundSongs = userInterface.getLibrarySongs();
                			if (foundSongs.size() == 0) {
                				System.out.println("Song is not in library");
                			}
                			else if (foundSongs.size() > 1) {
                				boolean songSelected = false;
                				while (songSelected == false) {
        	        				System.out.println("Enter artist of desired album");
        	        				for (Song foundSong : foundSongs) {
        	        					System.out.println(foundSong.getArtistName());
        	        				}
        	        				input = scanner.nextLine();
        	        				for (Song foundSong1 : foundSongs) {
        	        					if (foundSong1.getArtistName().equals(input)) {
        	        						userInterface.addToPlaylist(foundSong1, playlist);
        	        						songSelected = true;
        	                				String format = String.format("%s added to %s", foundSong1.getSongName(), playlist.getPlaylistName());
        	                				System.out.println(format);
        	        					}
        	        				}
        	        			}
                			}
                			else {
                				Song ourSong = foundSongs.get(0);
                				userInterface.addToPlaylist(ourSong, playlist);
                				String format = String.format("%s added to %s", ourSong.getSongName(), playlist.getPlaylistName());
                				System.out.println(format);
                			}
        				}
        			}
        		}
        		else if (input.equals("q")) {
        			storeMode = true;
        		}
        		else if (input.equals("logout")) {
        			loggedIn = false;
        		}
        		else if (input.equals("l")) {
        			for (Song song : userInterface.getLibrarySongs()) {
        				System.out.println(song.getSongName());
        			}
        		}
        		else if (input.equals("m")) {
        			ArrayList<Song> songs = new ArrayList<>();
        			System.out.println("Insert song title");
        			input = scanner.nextLine();
        			for (Song song : userInterface.getLibrarySongs()) {
        				if (song.getSongName().equals(input)) {
        					songs.add(song);
        				}
        			}
        			if (songs.size() == 0) {
        				System.out.println("Song does not exits in library");
        			}
        			else {
	        			for (Song song1 : songs) {
	        				System.out.println(song1.getSongName() + ", " + song1.getArtistName() + ", " + song1.getAlbumName());
	        			}
        			}
        		}
        		else if (input.equals("n")) {
        			System.out.println("Insert artist name");
        			input = scanner.nextLine();
        			boolean found = false;
        			for (Song song : userInterface.getLibrarySongs()) {
        				if (song.getArtistName().equals(input)) {
        					found = true;
        					System.out.println(song.getSongName() + ", " + song.getArtistName() + ", " + song.getAlbumName());
        				}
        			}
        			if (found == false) {
        				System.out.println("Artist has no songs in library");
        			}
        		}
        		else if (input.equals("o")) {
        			System.out.println("Insert album title");
        			input = scanner.nextLine();
        			boolean found = false;
        			for (Album album : userInterface.getLibraryAlbums()) {
        				if (album.getAlbumName().equals(input)) {
        					found = true;
		    				System.out.println("Album: " + album.getAlbumName());
		    				System.out.println("Artist: " + album.getArtistName());
		    				System.out.println("Released: " + album.getAlbumYear());
		    				System.out.println("Genre: " + album.getAlbumGenre());
		    				System.out.println("Songs:");
		    				for (Song song : album.getAllSongs()) {
		    					System.out.println("\t" + song.getSongName());
		    				}
        				}
        			}
        			if (found == false) {
        				System.out.println("Album does not exist within library");
        			}
        		}
        		else if (input.equals("p")) {
        			System.out.println("Insert artist name");
        			input = scanner.nextLine();
        			boolean found = false;
        			for (Album album : userInterface.getLibraryAlbums()) {
        				if (album.getArtistName().equals(input)) {
        					found = true;
		    				System.out.println("Album: " + album.getAlbumName());
		    				System.out.println("Artist: " + album.getArtistName());
		    				System.out.println("Released: " + album.getAlbumYear());
		    				System.out.println("Genre: " + album.getAlbumGenre());
		    				System.out.println("Songs:");
		    				for (Song song : album.getAllSongs()) {
		    					System.out.println("\t" + song.getSongName());
		    				}
        				}
        			}
        			if (found == false) {
        				System.out.print("Artist does not have any albums in library");
        			}
        		}
        		else {
	        		System.out.println("Invalid input selected: try again");
	        		continue;
        		}
        	}
        }
    }
}


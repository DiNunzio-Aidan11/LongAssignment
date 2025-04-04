import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.lang.String;
import java.io.IOException;
import java.io.FileWriter;

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
	 * First parses in the information to the store then calls for user inputs to process */
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter Album Text File: ");
        //String fileName = scanner.nextLine();

        File albumsFile = new File("albums.txt"); 
        File usersFile = new File("users.txt");

        if (!albumsFile.exists()) {  // Check if file exists before proceeding
            System.out.println("Error: File not found!");
            scanner.close();
            return;
        }

        ArrayList<Artist> store = MusicStore.parseAlbumTextFile("albums.txt");
        
        // lets the view class have a reference to the stores information 
        UI userInterface = new UI(store);
        
        boolean storeMode = true;
        boolean loggedIn = false;
        
        
        //TODO: add in stored information for users from other file
        Users users = new Users();
        User curUser = null;

        users.loadUsers();

        while (true) {
        	while (!loggedIn) {
                System.out.println("========= login menu ==========");
                System.out.println("Please Choose an Option:");
                System.out.println("Input '1': Log in");
                System.out.println("Input '2': Register User");
                System.out.println("Input '3': To Quit");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    File userFile = new File(username + ".txt");

                    if (userFile.exists()) {
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();

                        Scanner fileScanner = new Scanner(usersFile);
                        boolean authenticated = false;

                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            String[] parts = line.split(",");
                            String fileUsername = parts[0];
                            String encryptedPassword = parts[1];
                            String inputEncrypted = Password.encrypt(password);

                            if (username.equals(fileUsername) && inputEncrypted.equals(encryptedPassword)) {
                                System.out.println("Login successfull! Welcome, " + username + "!");
                                loggedIn = true;
                                curUser = users.getUser(username);
                                userInterface.setUser(curUser);
                                authenticated = true;
                                
                                break;
                            }
                        }
                        fileScanner.close();

                        if (!authenticated && !loggedIn) {
                            System.out.println("Invalid credentials, try again");
                        }
                    }
                    else {
                        System.out.println("User does not exist");
                    }

                }
                else if (choice.equals("2")) {
                	String username = "";
                	boolean uniqueUser = false;

                    Scanner fileScanner = new Scanner(usersFile);
                    ArrayList<String> fileUsers = new ArrayList<>();

                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String parts[] = line.split(",");
                        String fileUsername = parts[0];
                        fileUsers.add(fileUsername);
                    }

                    fileScanner.close();
                    while (!uniqueUser) {
                        System.out.print("Choose a username: ");
                        username = scanner.nextLine();
                        if (username.length() == 0) {
                            System.out.println("Can't have blank username");
                        }
                        else if (fileUsers.contains(username)) {
                            System.out.println("username already taken");
                        }
                        else {
                            uniqueUser = true;
                        }
                    }
                    System.out.print("Choose a password: ");
                    String password = scanner.nextLine();
                    password = Password.encrypt(password);

                    if (users.addUser(username, password, usersFile)) {
                        users.createUserDataFile(username);
                        System.out.println("You can now log in.");
                    }
                }
                else if (choice.equals("3")) {
                    scanner.close();
                	return;
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
	        			System.out.println("Artist does not have any songs in store");
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
                    if (curUser != null) {
                        FileWriter clearer = new FileWriter(curUser.getUsername() + ".txt", false);
                        clearer.close();
                        curUser.writeUserInfo();
                    }
	        		loggedIn = false;
	        	}
	        	else {
	        		System.out.println("Invalid input selected: try again");
	        		continue;
	        	}
        	}
        	else {
        		System.out.println("========== library ==========");
        		System.out.println("Input 'a': To add song/album/playlist to library");
        		System.out.println("Input 'b': list artists/album/playlists/favorites/songs");
        		System.out.println("Input 'c': search for songs by title/artist/albumn/genre");
        		System.out.println("Input 'd': print all songs in library in sorted order");
        		System.out.println("Input 'e': shuffle all songs in library");
        		System.out.println("Input 'f': rate song");
        		System.out.println("Input 'g': to (un)favorite song");
        		System.out.println("Input 'h': add song to playlist");
        		System.out.println("Input 'i': delete song from playlist");
        		System.out.println("Input 'j': view playlist");
        		System.out.println("Input 'k': remove playlist");
        		System.out.println("Input 'l': remove song from library");
        		System.out.println("Input 'm': remove album from library");
        		System.out.println("Input 'n': switch to store");
        		System.out.println("Input 'o': to shuffle playlist");
                System.out.println("Input 'p': play song");
                System.out.println("Input 'q': display recently played");
                System.out.println("Input 'r': display frequently played");
        		System.out.println("Input 'logout': to logout");
        		
        		String input = scanner.nextLine();
        		if (input.equals("a")) {
        			System.out.println("Input 'a': add playlist");
        			System.out.println("Input 'b': add song to library");
        			System.out.println("Input 'c': add album to library");
        			input = scanner.nextLine();
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
    	        						ArrayList<Album> albums = songArtist.getAllAlbums();
    	        						for (Album album : albums) {
    	        							if (album.getCertainSong(input) != null) {
    	        								userInterface.addAlbumToLibrary(album);
    	        								break;
    	        							}
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
    						ArrayList<Album> albums = songArtist.getAllAlbums();
    						for (Album album : albums) {
    							if (album.getCertainSong(input) != null) {
    								userInterface.addAlbumToLibrary(album);
    								break;
    							}
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
    	        						// adds all songs in album
    	                				for (Song song : foundAlbum1.getAllSongs()) {
    	                					userInterface.addSongToLibrary(song);
    	                				}
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
            				// adds all songs in album
            				for (Song song : ourAlbum.getAllSongs()) {
            					userInterface.addSongToLibrary(song);
            				}
    						Artist songArtist = userInterface.searchArtistFromAlbum(ourAlbum.getArtistName());
    						if (!userInterface.getLibraryArtists().contains(songArtist) && songArtist != null) {
    							userInterface.addArtistToLibrary(songArtist);
    						}
            				String format = String.format("%s added to library", ourAlbum.getAlbumName());
            				System.out.println(format);
            			}
            		}
            		else {
            			System.out.print("Invalid command");
            		}
        			
        		}
        		else if (input.equals("b")) {
        			System.out.println("Input 'a': list artists");
        			System.out.println("Input 'b': list albums");
            		System.out.println("Input 'c': list playlists");
            		System.out.println("Input 'd': list favorites");
            		System.out.println("Input 'e': list songs in library");
            		input = scanner.nextLine();
            		
            		if (input.equals("a")) {
            			for (Artist artist : userInterface.getLibraryArtists()) {
            				System.out.println(artist.getArtistName());
            			}
            			
            		}
            		else if (input.equals("b")) {
            			for (Album album : userInterface.getLibraryAlbums()) {
            				System.out.println(album.getAlbumName());
            			}
            		}
            		else if (input.equals("c")) {
            			for (Playlist playlist : userInterface.getLibraryPlaylists()) {
            				System.out.println(playlist.getPlaylistName());
            			}
            		}
            		else if (input.equals("d")) {
            			for (Song song : userInterface.getLibraryFavorites()) {
            				System.out.println(song.getSongName());
            			}
            		}
            		else if (input.equals("e")) {
            			for (Song song : userInterface.getLibrarySongs()) {
            				System.out.println(song.getSongName());
            			}
            		}
            		else {
            			System.out.println("Invalid command");
            		}
        		}
        		else if (input.equals("c")) {
        			System.out.println("Input 'a': search for songs by title");
    	        	System.out.println("Input 'b': search for songs by artist");
    	        	System.out.println("Input 'c': search for album by title");
    	        	System.out.println("Input 'd': search for album by artist");
    	        	System.out.println("Input 'd': search for album by genre");
    	        	System.out.println("Input 'e': search by genre title");
    	        	input = scanner.nextLine();
    	        	
    	        	if (input.equals("a")) {
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
            			System.out.println("Would you like to get its album information y/n");
            			int cont = 1;
            			while (cont != 0) {
            				input = scanner.nextLine();
            				if (input.equals("y")) {
        	        			for (Song song1 : songs) {
        	        				String name = song1.getArtistName();
        	        				String albumName = song1.getAlbumName();
        	        				for (Artist art : userInterface.getLibraryArtists()) {
        	        					if (art.getArtistName().equals(name)) {
        	        						for (Album alb : art.getAllAlbums()) {
        	        							if (albumName.equals(alb.getAlbumName())) {
        	        								System.out.println("Album: " + alb.getAlbumName());
        	        								System.out.println("Artist: " + alb.getArtistName());
        	        								System.out.println("Released: " + alb.getAlbumYear());
        	        								System.out.println("Genre: " + alb.getAlbumGenre());
        	        								System.out.println("Songs:");
        	        								for (Song song : alb.getAllSongs()) {
        	        									System.out.println("\t" + song.getSongName());
        	        								}
        	        							}
        	        						}
        	        					}
        	        				}
            						
        	        			}
            					cont = 0;
        	        		}
            				else if (input.equals("n")) {
            					cont = 0;
            				}
            				else {
            					System.out.println("use y or n");
            				}
            			}
    	        	}
            		else if (input.equals("b")) {
            			System.out.println("Insert artist name");
            			Song songs = null;
            			input = scanner.nextLine();
            			boolean found = false;
            			for (Song song : userInterface.getLibrarySongs()) {
            				if (song.getArtistName().equals(input)) {
            					songs = song;
            					found = true;
            					System.out.println(song.getSongName() + ", " + song.getArtistName() + ", " + song.getAlbumName());
            				}
            			}
            			if (found == false) {
            				System.out.println("Artist has no songs in library");
            			}
            			else {
                			System.out.println("Would you like to get its album information y/n");
                			int cont = 1;
                			while (cont != 0) {
                				input = scanner.nextLine();
                				if (input.equals("y")) {
            	        			String name = songs.getArtistName();
            	        			String albumName = songs.getAlbumName();
            	        			for (Artist art : userInterface.getLibraryArtists()) {
            	        				if (art.getArtistName().equals(name)) {
            	        					for (Album alb : art.getAllAlbums()) {
            	        						if (albumName.equals(alb.getAlbumName())) {
            	        							System.out.println("Album: " + alb.getAlbumName());
            	        							System.out.println("Artist: " + alb.getArtistName());
            	        							System.out.println("Released: " + alb.getAlbumYear());
            	        							System.out.println("Genre: " + alb.getAlbumGenre());
            	        							System.out.println("Songs:");
            	        							for (Song song : alb.getAllSongs()) {
            	        								System.out.println("\t" + song.getSongName());
            	        							}
            	        						}
            	        					}
            	        				}
            	        			}
            	        			cont = 0;
            	        		}
                				else if (input.equals("n")) {
                					cont = 0;
                				}
                				else {
                					System.out.println("use y or n");
                				}
                			}
            			}
            		}
            		else if (input.equals("c")) {
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
            		else if (input.equals("d")) {
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
            				System.out.println("Artist does not have any albums in library");
            			}
            		}
            		else if (input.equals("e")) {
            			System.out.print("What genre: ");
            			input = scanner.nextLine();
            			for (Album album : userInterface.getLibraryAlbums()) {
            				if (album.getAlbumGenre().equals(input)) {
            					for (Song song : album.getAllSongs()) {
            						System.out.println(song.getSongName() + " By: " + song.getArtistName());
            					}
            				}
            			}
            			
            		}
            		else {
            			System.out.println("Invalid command");
            		}
    	        	
        		}
        		else if (input.equals("d")) {
        		    ArrayList<Song> songs = userInterface.getLibrarySongs();
        		    
        			System.out.println("How would you like to sort");
        			System.out.println("Input 'a': by song title");
        			System.out.println("Input 'b': artist");
        			System.out.println("Input 'c': by rating");
        			input = scanner.nextLine();
        	        Comparator<Song> comparator = null;
        	        switch (input) {
        	            case "a":
        	                comparator = Comparator.comparing(Song::getSongName);
        	                break;
        	            case "b":
        	                comparator = Comparator.comparing(Song::getArtistName);
        	                break;
        	            case "c":
        	            	comparator = Comparator.comparing(Song::getRating, Comparator.nullsLast(Comparator.reverseOrder()));
        	                break;
        	            default:
        	                System.out.println("Invalid input");
        	                scanner.close();
        	                return;
        	        }

        	        // Use Collections.sort with the chosen comparator
        	        songs.sort(comparator);

        	        // Print sorted songs
        	        for (Song song : songs) {
        	            System.out.println(song.getSongName() + ", " + song.getArtistName() + " (Rating: " + song.getRating() + ")");
        	        }
        		    System.out.println();
        		}
        		else if (input.equals("e")) {
        			System.out.println("shuffled songs");
        			userInterface.shuffleSongs();
        		}
  		
        		else if (input.equals("f")) {
        			System.out.println("Select song to rate");
        			input = scanner.nextLine();
        			ArrayList<Song> songs = userInterface.getLibrarySongs();
        			ArrayList<Song> foundSongs = new ArrayList<>();
        			for (Song song : songs) {
        				if (song.getSongName().equals(input)) {
        					foundSongs.add(song);
        				}
        			}
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
        		else if (input.equals("g")) {
        			System.out.println("Select song to (un)favorite");
        			input = scanner.nextLine();
        			ArrayList<Song> songs = userInterface.getLibrarySongs();
        			ArrayList<Song> foundSongs = new ArrayList<>();
        			for (Song song : songs) {
        				if (song.getSongName().equals(input)) {
        					foundSongs.add(song);
        				}
        			}
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
	        						userInterface.markFavorite(ourSong, !(ourSong.isFavorite()));	
	        					}
	        				}
        				}
        			}
        			else {
        				userInterface.markFavorite(foundSongs.get(0), true);
        			}
        		}
        		
        		else if (input.equals("h")) {
        			System.out.println("Enter playlist you with to add song to");
        			input = scanner.nextLine();
        			for (Playlist playlist : userInterface.getLibraryPlaylists()) {
        				if (playlist.getPlaylistName().equals(input)) {
        					System.out.println("Enter song you wish to add to playlist");
        					input = scanner.nextLine();
        					ArrayList<Song> songs = userInterface.getLibrarySongs();
        					ArrayList<Song> foundSongs = new ArrayList<>();
        					for (Song song : songs) {
        						if (song.getSongName().equals(input)) {
        							foundSongs.add(song);
        						}
        					}
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
        		else if (input.equals("i")) {
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
        			System.out.println("Song or Playlist not in library");
        		}
        		else if (input.equals("j")) {
        			boolean found = false;
        			System.out.println("Enter playlist you wish to view");
        			input = scanner.nextLine();
        			for (Playlist playlist : userInterface.getLibraryPlaylists()) {
        				if (playlist.getPlaylistName().equals(input)) {
        					found = true;
        					for (Song song : playlist.getPlaylistSongs()) {
        						System.out.println(song.getSongName() + " - " + song.getArtistName());
        					}
        				}
        			}
        			if (found == false) {
        				System.out.println("Playlist not found");
        			}
        		}


                else if (input.equals("k")) {
                    System.out.print("Enter the name of the album you want to remove: ");
                    input = scanner.nextLine();
                    ArrayList<Album> foundAlbum = new ArrayList<>();
                    for (Album album : userInterface.getLibraryAlbums()) {
                        if (album.getAlbumName().equals(input)) {
                            foundAlbum.add(album);
                        }
                    }
                    if (foundAlbum.size() == 0) {
                        System.out.println("Album not in library");
                    }
                    else if (foundAlbum.size() != 1){
                        System.out.println("From which artist: ");
                        for (int i = 0; i < foundAlbum.size(); i++) {
                            System.out.println(foundAlbum.get(i).getArtistName());
                        }
                        input = scanner.nextLine();
                        for (int i = 0; i < foundAlbum.size(); i++) {
                            if (foundAlbum.get(i).getArtistName().equals(input)) {
                                userInterface.removeAlbum(foundAlbum.get(i));
                                System.out.println("Succesfully removed album");
                                break;
                            }
                        }	
                    }
                    else {
                        userInterface.removeAlbum(foundAlbum.get(0));
                        System.out.println("Succesfully removed album");
                    }
                }            

        		else if (input.equals("l")) {
        			System.out.print("Enter the name of the song you want to remove: ");
        			input = scanner.nextLine();
        			ArrayList<Song> foundSongs = new ArrayList<>();
        			for (Song song : userInterface.getLibrarySongs()) {
        				if (song.getSongName().equals(input)) {
        					foundSongs.add(song);
        				}
        			}
        			if (foundSongs.size() == 0) {
        				System.out.println("Song not in library");
        			}
        			else if (foundSongs.size() != 1){
        				System.out.println("From which artist: ");
        				for (int i = 0; i < foundSongs.size(); i++) {
        					System.out.println(foundSongs.get(i).getArtistName());
        				}
        				input = scanner.nextLine();
        				for (int i = 0; i < foundSongs.size(); i++) {
        					if (foundSongs.get(i).getArtistName().equals(input)) {
        						userInterface.removeSong(foundSongs.get(i));
        						System.out.println("Succesfully removed song");
        						break;
        					}
        				}	
        			}
        			else {
        				userInterface.removeSong(foundSongs.get(0));
        				System.out.println("Succesfully removed song");
        			}
        			
        		}
        		else if (input.equals("m")) {
        			System.out.print("Enter the name of the album you want to remove: ");
        			input = scanner.nextLine();
        			ArrayList<Album> foundAlbum = new ArrayList<>();
        			for (Album album : userInterface.getLibraryAlbums()) {
        				if (album.getAlbumName().equals(input)) {
        					foundAlbum.add(album);
        				}
        			}
        			if (foundAlbum.size() == 0) {
        				System.out.println("Album not in library");
        			}
        			else if (foundAlbum.size() != 1){
        				System.out.println("From which artist: ");
        				for (int i = 0; i < foundAlbum.size(); i++) {
        					System.out.println(foundAlbum.get(i).getArtistName());
        				}
        				input = scanner.nextLine();
        				for (int i = 0; i < foundAlbum.size(); i++) {
        					if (foundAlbum.get(i).getArtistName().equals(input)) {
        						userInterface.removeAlbum(foundAlbum.get(i));
        						System.out.println("Succesfully removed album");
        						break;
        					}
        				}	
        			}
        			else {
        				userInterface.removeAlbum(foundAlbum.get(0));
        				System.out.println("Succesfully removed album");
        			}
        			
        		}
        		else if (input.equals("n")) {
        			storeMode = true;
        		}
        		else if (input.equals("o")) {
        			System.out.println("Enter the name of the playlist you want to shuffle");
        			input = scanner.nextLine();
        			userInterface.shufflePlaylist(input);
        			storeMode = true;
        		}
                else if (input.equals("p")) {
                    System.out.println("Enter the name of the song you would like to play");
                    input = scanner.nextLine();
                    boolean foundSong = false;
                    for (Song song : userInterface.getLibrarySongs()) {
                        if (song.getSongName().equals(input)) {
                            System.out.println("Playing " + input);
                            userInterface.playSong(song);
                            foundSong = true;
                            break;
                        }
                    }
                    if (foundSong == false) {
                        System.out.println("Song does not exist in library");
                    }
                }
                else if (input.equals("q")) {
                    for (Song song : userInterface.getRecentlyPlayed()) {
                        System.out.println(song.getSongName());
                    }
                }
                else if (input.equals("r")) {
                    for (Song song : userInterface.getFrequentlyPlayed()) {
                        System.out.println(song.getSongName());
                    }
                }
        		else if (input.equals("logout")) {
                    if (curUser != null) {
                        FileWriter clearer = new FileWriter(curUser.getUsername() + ".txt", false);
                        clearer.close();
                        curUser.writeUserInfo();
                    }
        			loggedIn = false;
        		}
        		else {
	        		System.out.println("Invalid input selected: try again");
	        		continue;
        		}
        	}
        }
    }
}

package model;

import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Scanner;

public class Users {
    private HashMap<String, User> hash;

    public Users() {
        this.hash = new HashMap<>();
    }

    public void createUserDataFile(String username) throws IOException {
        File userDataFile = new File(username + ".txt");
        userDataFile.createNewFile();
    }

    public boolean addUser(String username, String password, File file) {
        if (this.userExists(username)) {
            System.out.println("Username already exists");
            return false;
        }
        
        try {
        	FileWriter fileWriter = new FileWriter(file, true);
        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        	
        	bufferedWriter.write(username + "," + password + "\n");
        	bufferedWriter.close();
        	fileWriter.close();
        }
        
        catch (IOException e) {
        	System.err.println("File could not be accessed: " + e.getMessage());
        }
        
        hash.put(username, new User(username, password));
        return true;
    }

    // debug
    public User getUser(String username) {
        return hash.get(username);
    }

    public boolean userExists(String username) {
    	// returns true if a user exists false otherwise
        return hash.containsKey(username);
    }

    public boolean authenticate(String username, String password) {
    	// used to check if the password that corresponds with a username is correct
        User user = hash.get(username);
        if (user != null) {
        	return user.getPassword().equals(password);
        }
        return false;
    }

    public void loadUsers() throws IOException {
        File file = new File("users.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] parts = scanner.nextLine().split(",");
            this.hash.put(parts[0], new User(parts[0], parts[1]));
        }
        scanner.close();
    }
}


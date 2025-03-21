package model;

import java.util.HashMap;

public class Users {
    private HashMap<String, User> hash;

    public Users() {
        this.hash = new HashMap<>();
    }

    public boolean addUser(String username, String password) {
        if (this.userExists(username)) {
            System.out.println("Username already exists");
            return false;
        }
        //TODO: add it to a file
        hash.put(username, new User(username, password));
        return true;
    }

    public User getUser(String username) {
    	if (userExists(username)) {
    		return hash.get(username);
    	}
    	return null;
    }

    public boolean removeUser(String username) {
    	// removes user if applicable 
        if (this.userExists(username)) {
        	//TODO: remove user from file
        	hash.remove(username);
            return true;
        }
        System.out.println("User not found.");
        return false;
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
        
}


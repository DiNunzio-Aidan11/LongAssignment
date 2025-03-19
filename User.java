package model;

public class User extends LibraryModel {
    private String username;
    private String password; 

    public User(String username, String password) {
        super(); // Calls LibraryModel constructor
        this.username = username;
        this.password = password;
    }
    // add in methods    
}

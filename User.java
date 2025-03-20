package model;

public class User extends LibraryModel {
    private String username;
    private String password; 

    public User(String username, String password) {
        super(); // Calls LibraryModel constructor
        this.username = username;
        this.password = password;
    }

	public String getPassword() {
		return password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void saveToDatabase() {
		// TODO: saves to file
	}
    
    
}

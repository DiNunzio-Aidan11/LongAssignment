package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import model.Users;

public class TestUsers {
	File testFile = new File("testing.txt");
	
	@Test
	public void testAdding() {
		Users users = new Users();
		users.addUser("Joe", "123", testFile);
		assertTrue(users.userExists("Joe"));
		
		// testing duplicate username
		assertFalse(users.addUser("Joe", "password", testFile));
	}
	
	@Test
	public void testGetUser() {
		Users users = new Users();
		users.addUser("Joe", "123", testFile);
		assertNotNull(users.getUser("Joe"));
		
		// non existing user
		assertNull(users.getUser("George"));
	}
	
	@Test
	public void testUserExists() {
		Users users = new Users();
		assertFalse(users.userExists("Joe"));
		
		users.addUser("Joe", "123", testFile);
		assertTrue(users.userExists("Joe"));
		
	}
	
	@Test
	public void testAuthentication() {
		Users users = new Users();
		users.addUser("Joe", "123", testFile);
		assertFalse(users.authenticate("Joe", "wrongPassword"));
		assertTrue(users.authenticate("Joe", "123"));
		
		// non added user
		assertFalse(users.authenticate("FakeUser", "123"));
	}

}


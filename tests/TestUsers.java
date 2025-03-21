package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Users;

public class TestUsers {
	
	@Test
	public void testAdding() {
		Users users = new Users();
		users.addUser("Joe", "123");
		assertTrue(users.userExists("Joe"));
		
		// testing duplicate username
		assertFalse(users.addUser("Joe", "password"));
	}
	
	@Test
	public void testGetUser() {
		Users users = new Users();
		users.addUser("Joe", "123");
		assertNotNull(users.getUser("Joe"));
		
		// non existing user
		assertNull(users.getUser("George"));
	}
	
	@Test
	public void testRemoveUser() {
		Users users = new Users();
		users.addUser("Joe", "123");
		
		users.removeUser("Joe");
		assertNull(users.getUser("Joe"));
	}
	
	@Test
	public void testUserExists() {
		Users users = new Users();
		assertFalse(users.userExists("Joe"));
		
		users.addUser("Joe", "123");
		assertTrue(users.userExists("Joe"));
		
	}
	
	@Test
	public void testAuthentication() {
		Users users = new Users();
		users.addUser("Joe", "123");
		assertFalse(users.authenticate("Joe", "wrongPassword"));
		assertTrue(users.authenticate("Joe", "123"));
		
		// non added user
		assertFalse(users.authenticate("FakeUser", "123"));
	}

}



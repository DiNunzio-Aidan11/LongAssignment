package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import org.junit.Test;

import model.Password;


public class TestPassword {
	@Test
    public void testEncryption() {
    	String encrypt = Password.encrypt("password");
    	String unencrypt = "password";
    	assertFalse(encrypt.equals(unencrypt));
    }
	
	@Test
	public void testUppercase() {
		String upper = Password.encrypt("PASSWORD");
		String lower = Password.encrypt("password");
		assertFalse(upper.equals(lower));
	}
	
	@Test
	public void same() {
		String encrypt1 = Password.encrypt("password");
		String encrypt2 = Password.encrypt("password");
		assertEquals(encrypt1, encrypt2);
	}
}


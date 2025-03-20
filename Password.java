package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
    public static String encrypt(String password) {
        String salt = getFixedSalt();
        String algorithm = "SHA-256";
        MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			return "Error algorithm SHA-256 not found";
		}
        md.update(salt.getBytes()); // Add salt to the digest
        byte[] hashedBytes = md.digest(password.getBytes()); // Hash the password
        return bytesToHex(hashedBytes);

    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    
    private static String getFixedSalt() {
        return "1849nh7l!?f$32sA";  // This salt will always be the same through runs
    }
}


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class EncryptPassword {
	
	public static String encrypt(String password, String salt, String algorithm) throws NoSuchAlgorithmException {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(salt.getBytes()); // Add salt to the digest
			byte[] hashedBytes = md.digest(password.getBytes()); // Hash the password
			return bytesToHex(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Hashing algorithm not found: " + algorithm, e);
	    }
	}
	
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
	
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes salt
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Password or 'q' to leave");
		String userInput = scanner.nextLine();
		String salt = generateSalt();
		while (!userInput.equals("q")) {
			try {
	            System.out.println(encrypt(userInput, salt, "SHA-256"));
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println("Error: Hashing algorithm not found.");
	            e.printStackTrace(); // Prints detailed error for debugging
	        }
			userInput = scanner.nextLine();
		}
		scanner.close();
	}

}

package dat.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HashPass {

	private static String hex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte aByte : bytes)
			result.append(String.format("%02x", aByte));
		return result.toString().toUpperCase();
	}

	public static String encode(char[] password) {
		byte[] salt = new byte[16];
		for (byte i = 0; i < 16; i++)
			salt[i] = i;
		byte[] hash;
		hash = null;
		try {
			KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		assert hash != null;
		return hex(hash);
	}

	public static boolean isPassHash(char[] pass, String hash) {
		String passHash = encode(pass);
		if (passHash.length() != hash.length()) return false;
		for (int i = 0; i < passHash.length(); i++)
			if (passHash.charAt(i) != hash.charAt(i)) return false;
		return true;
	}

}

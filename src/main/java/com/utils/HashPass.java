package com.utils;

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

    public static String encode(String password) {
        byte[] salt = new byte[16];
        for (byte i = 0; i < 16; i++)
            salt[i] = i;
        byte[] hash;
        hash = null;
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert hash != null;
        return hex(hash);
    }

    public static boolean isPassHash(String pass, String hash) {
        pass = encode(pass);
        if (pass.length() != hash.length()) return false;
        for (int i = 0; i < pass.length(); i++)
            if (pass.charAt(i) != hash.charAt(i)) return false;
        return true;
    }

}

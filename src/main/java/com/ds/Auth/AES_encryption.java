package com.ds.Auth;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES encryption and validation.
 */
public class AES_encryption {

    private static final String key = "fF/fWxwpg]t1%~|9";

    private static final SecureRandom random = new SecureRandom();

    public byte[] encrypt(String plainpassword, byte[] salt) {
        byte[] cipherBytes = null;
        try {
            Cipher ci = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKey keySpec = new SecretKeySpec(key.getBytes(), "AES");

            // GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);

            // Init the Cipher with ENCRYPT_MODE
            ci.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

            cipherBytes = ci.doFinal(plainpassword.getBytes());
        } catch (Exception e) {
            System.out.println("Encryption error: " + e.toString());
        }
        return cipherBytes;
    }

    public byte[] generateSalt() {
        byte [] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return saltBytes;
    }

    public boolean validatePassword(String pass, byte[] salt, byte[] encryptedPass) {
        byte[] validatePassword = encrypt(pass, salt);
        return Arrays.equals(validatePassword, encryptedPass);
    }
}
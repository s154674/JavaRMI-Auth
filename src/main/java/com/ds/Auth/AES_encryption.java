package com.ds.Auth;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
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
            //We use the PBKDF2WithHmacSHA256 algorithm 
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 256);
            SecretKey tmpKey = factory.generateSecret(spec);
            System.out.println(tmpKey);
            SecretKeySpec keySpec = new SecretKeySpec(tmpKey.getEncoded(), "AES");
            
            // GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, salt);
            
            // Init the Cipher with ENCRYPT_MODE
            Cipher ci = Cipher.getInstance("AES/GCM/NoPadding");
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

    public void Auth(String u, String p, List<User> users) {
        boolean isloggedin = false;
        System.err.println("Function requested by: " + u);
        for(User user: users) {
            if(u.equals(user.getUsername())){
                if(validatePassword(p, user.getSalt(), user.getEncryptedPassword())) {
                    isloggedin = true;
                } else {
                    isloggedin = false;
                }
            } 
        }
        if(!isloggedin) {
            System.err.println("User could not login: " + u); 
            throw new SecurityException("Not authorized");
        }
    }
}
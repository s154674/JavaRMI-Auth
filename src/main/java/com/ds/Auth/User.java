package com.ds.Auth;

import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    private String username = null;
    private byte[] encryptedPassword = null;
    private byte[] salt = null;
    private String role = null;

    public User(String name, String pass){

        AES_encryption aes = new AES_encryption();

        username = name;
        salt = aes.generateSalt();
        encryptedPassword = aes.encrypt(pass, salt);

    }

    public String getUsername() {
        return username;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getRole() { return role; }

}

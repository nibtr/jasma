/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Hieu
 */
public class PasswordUtil {
    private static final int SALT_LENGTH = 16; // in bytes
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256; // in bits
    
    public static String hash(String password) {
        try {
            // Generate a random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            
//            Hash the password with the salt
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            
//            Concatenate the salt to the hashed password
            String saltAndHash = Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
            
            return saltAndHash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static boolean comparePassword(String inputPassword, String storedSaltAndHash) {
        try {
//            Split the salt and hash into seperate strings
            String[] parts = storedSaltAndHash.split(":");
            byte[] storedSalt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHash = Base64.getDecoder().decode(parts[1]);
            
//            Hash the input password with the stored salt
            KeySpec storedSpec = new PBEKeySpec(inputPassword.toCharArray(), storedSalt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory storedFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] inputHash = storedFactory.generateSecret(storedSpec).getEncoded();
            
            // Compare the stored hash with the input hash
            return MessageDigest.isEqual(storedHash, inputHash);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

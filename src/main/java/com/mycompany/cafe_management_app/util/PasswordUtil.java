/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Hieu
 */
public class PasswordUtil {
    private static final int LOG_ROUNDS = 12;
    
    public static String hash(String password) {
        try {
            String salt = BCrypt.gensalt(LOG_ROUNDS);
            String hashedPassword = BCrypt.hashpw(password, salt);
            String storedPassword = salt + ":" + hashedPassword;
            
            return storedPassword;
            
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean comparePassword(String inputPassword, String storedPassword) {
        try {
//            Split the salt and hash into seperate strings
            String[] parts = storedPassword.split(":");
            String storedSalt = parts[0];
            String storedHashedPassword = parts[1];
            
//            Hash the input password with the stored salt
            String inputHashedPassword = BCrypt.hashpw(inputPassword, storedSalt);
            
            // Compare the stored hash with the input hash
            return BCrypt.checkpw(inputHashedPassword, storedHashedPassword);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

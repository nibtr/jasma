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
            
            return hashedPassword;
            
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
            return BCrypt.checkpw(inputPassword, storedPassword);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.util;

/**
 *
 * @author Hieu
 */
public class UserSession {
    private static UserSession instance;
    private Long userID;
    
    private UserSession() {}
    
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        
        return instance;
    }
    
    public void setUserID(Long id) {
        userID = id;
    }
    
    public Long getUserID() {
        return userID;
    }
    
    public void clear() {
        userID = null;
    }
}

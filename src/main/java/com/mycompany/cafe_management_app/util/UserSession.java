/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.util;

import com.mycompany.cafe_management_app.model.Staff;

/**
 *
 * @author Hieu
 */
public class UserSession {
    private static UserSession instance;
    private Staff currentStaff;
    
    private UserSession() {
        
    }
    
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        
        return instance;
    }
    
    public void setStaff(Staff t) {
        currentStaff = t;
    }
    
    public Staff getStaff() {
        return currentStaff;
    }
    
    public void clear() {
        currentStaff = null;
    }
}

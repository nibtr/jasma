package com.mycompany.cafe_management_app.controller;


import com.mycompany.cafe_management_app.ui.DashboardAdminUI.Notification;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author namho
 */
public class NotificationController {
    private Notification noti;
    
    public NotificationController(String notification) {
        noti = new Notification();
        noti.getLabel().setText(notification);
        noti.setVisible(true);
    }
}

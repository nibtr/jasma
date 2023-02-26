/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.ui.DashboardAdminUI.DashboardAdminUI;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.StaffItem;
import javax.swing.JPanel;

/**
 *
 * @author namho
 */
public class DashboardAdminController {
    private DashboardAdminUI dashboardAdminUI;
    
    public DashboardAdminController() {
        initController();
    }
    
    private void initController() {
        dashboardAdminUI = new DashboardAdminUI();
        JPanel listStaff = dashboardAdminUI.getListStaff();
        for (int i = 0; i < 30; i++) {
            
            listStaff.add(new StaffItem("", "", ""));
        }
        
        dashboardAdminUI.setVisible(true);
    }
}

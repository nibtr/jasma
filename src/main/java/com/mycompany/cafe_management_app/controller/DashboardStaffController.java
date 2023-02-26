package com.mycompany.cafe_management_app.controller;


import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DashboardStaffUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author namho
 */
public class DashboardStaffController {
    private DashboardStaffUI dashboardStaffUI;
    
    public DashboardStaffController() {
        initController();
    }
  
    private void initController() {
        dashboardStaffUI = new DashboardStaffUI();
        dashboardStaffUI.setVisible(true);
    }
}

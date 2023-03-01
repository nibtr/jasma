package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DashboardStaffUI;
import com.mycompany.cafe_management_app.service.StaffService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private final StaffService staffService;

    public DashboardStaffController() {
        initController();
        staffService = new StaffService();
        this.dashboardStaffUI = new DashboardStaffUI();
        this.dashboardStaffUI.getCheckInOutButton().addActionListener(new CheckInOutButtonListener());
    }

    public void checkIn() {
        LocalDateTime currentTime = LocalDateTime.now();
        staffService.checkIn(currentTime);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = currentTime.format(formatter);
        JOptionPane.showMessageDialog(dashboardStaffUI, "     CHECK IN SUCCESSFUL!\n" + "        " + formatDateTime);
    }

    public void checkOut() {
        LocalDateTime currentTime = LocalDateTime.now();
        staffService.checkOut(currentTime);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = currentTime.format(formatter);
        JOptionPane.showMessageDialog(dashboardStaffUI, "     CHECK OUT SUCCESSFUL!\n" + "        " + formatDateTime);
        
        System.exit(0);
    }

    private class CheckInOutButtonListener implements ActionListener {
        private boolean checkedIn = true;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkedIn) {
                checkIn();
            } else {
                checkOut();
            }
        }
    }

    public DashboardStaffUI getDashboardStaffUI() {
        return dashboardStaffUI;
    }

    private void initController() {
        dashboardStaffUI = new DashboardStaffUI();
        // dashboardStaffUI.setVisible(true);
    }
}

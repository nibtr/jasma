package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DashboardStaffUI;
import com.mycompany.cafe_management_app.service.StaffService;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.OrderHistory;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
    private StaffService staffService;
    private JPanel wrapListBill;


    public DashboardStaffController() {
        initController();
    }

    public void checkIn() {
        LocalDateTime currentTime = LocalDateTime.now();
        staffService.checkIn(currentTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = currentTime.format(formatter);
        JOptionPane.showMessageDialog(dashboardStaffUI, "     CHECK IN SUCCESSFUL!\n" + "        " + formatDateTime);
    }

    public void checkOut() {
        JFrame jframe = new JFrame("EXIT");
        if (JOptionPane.showConfirmDialog(jframe, "Confirm if you want to exit", "Time Keeping",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
        }

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
                checkedIn = false;

            } else {
                checkOut();
            }
        }
    }

    public DashboardStaffUI getDashboardStaffUI() {
        return dashboardStaffUI;
    }

    private void renderListOrder() {
        List<Bill> listBill = staffService.getAllBill();
        for (int i = 0; i < listBill.size(); i++) {
            Bill tmpOrder = listBill.get(i);
            LocalDateTime time = tmpOrder.getTimeCreated();
            Double totalPriceLabel = tmpOrder.getTotalPrice();
            Double receiveAmountLabel = tmpOrder.getReceivedAmount();
            Double returnAmountLabel = tmpOrder.getReturnedAmount();
            OrderHistory tmp = new OrderHistory(tmpOrder, time, totalPriceLabel, receiveAmountLabel, returnAmountLabel);
            wrapListBill.add(tmp);
        }
    }

    private void initController() {
        staffService = new StaffService();

        // check in/out button
        dashboardStaffUI = new DashboardStaffUI();
        dashboardStaffUI.getCheckInOutButton().addActionListener(new CheckInOutButtonListener());

        // show list bill
        wrapListBill = dashboardStaffUI.getContainerListBill();
        renderListOrder();

        dashboardStaffUI.setVisible(true);
    }
}

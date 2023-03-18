/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.dao.BillDetailDao;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.BillDetail;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.BillDetailsUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DishDetailsInBilIUI;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JPanel;
import org.hibernate.type.ListType;

/**
 *
 * @author Administrator
 */
public class BillDetailsController {

    public BillDetailsController(Bill bill, List<BillDetail> list) {
        BillDetailsUI billdetails = new BillDetailsUI();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = bill.getTimeCreated().format(formatter);

        JPanel container = billdetails.getContainer();

        for (BillDetail bd : list) {
            container.add(new DishDetailsInBilIUI(bd));
        }
        
        String totalPrice = String.format("%,.0f", bill.getTotalPrice());
        String receiveAmount = String.format("%,.0f", bill.getReceivedAmount());
        String returnAmount = String.format("%,.0f", bill.getReturnedAmount());
        
        billdetails.settimeBillLabel(formatDateTime);
        billdetails.setmethodBillLabel(bill.getPaymentMethod());
        billdetails.settotalBillLabel(totalPrice);
        billdetails.setreceiveBillLabel(receiveAmount);
        billdetails.setreturnBillLabel(returnAmount);

        billdetails.setVisible(true);
    }
}

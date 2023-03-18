/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.BillDetailsUI;
import java.time.format.DateTimeFormatter;



/**
 *
 * @author Administrator
 */
public class BillDetailsController {
    public BillDetailsController(Bill bill){
        BillDetailsUI billdetails = new BillDetailsUI();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = bill.getTimeCreated().format(formatter);
        
        
        
        
        billdetails.settimeBillLabel(formatDateTime);
//        billdetails.setstaffBillLabel(bill.getTimeCreated().toString());
        billdetails.setmethodBillLabel(bill.getPaymentMethod().toString());
        billdetails.settotalBillLabel(bill.getTotalPrice().toString());
        billdetails.setreceiveBillLabel(bill.getReceivedAmount().toString());
        billdetails.setreturnBillLabel(bill.getReturnedAmount().toString());
        
        billdetails.setVisible(true);
    }
}

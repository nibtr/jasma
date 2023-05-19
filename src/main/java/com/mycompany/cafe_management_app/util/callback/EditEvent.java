/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.util.callback;

import com.mycompany.cafe_management_app.controller.DashboardAdminController.EditFunction;
import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.NewStaffForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author namho
 */
public class EditEvent {
    
    private EditStaffInterface func;
    private NewStaffForm form;
    private Staff editedStaff;
    
   public EditEvent(EditStaffInterface func) {
       this.func = func;
   }
    
    public void edit(Staff staff) {
        editedStaff = staff;
        String name = staff.getName();
        String dob = staff.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM yyyy"));
        String phone = staff.getPhoneNumber();
        String pos = staff.getPosition();
        String tmp = dob;
        String day = tmp.substring(0, 2);
        tmp = dob;
        String month = tmp.substring(3, 5);
        tmp = dob;
        String year = tmp.substring(6, 10);
        form = new NewStaffForm(name, phone, pos, day, month, year);
        JButton saveBtn = form.getSaveButton();
        saveBtn.addActionListener(new callSaveAPI());
        form.setVisible(true);
    }
    
    private class callSaveAPI implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.println(editedStaff.getId());
            func.execute(form, editedStaff);
        }
    }
}

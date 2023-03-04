/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.DashboardAdminUI;
import com.mycompany.cafe_management_app.util.callback.DeleteEvent;
import com.mycompany.cafe_management_app.util.callback.EditEvent;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.NewStaffForm;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.StaffItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author namho
 */
public class DashboardAdminController {
    private DashboardAdminUI dashboardAdminUI;
    private AdminService adminService;
    private JButton addStaffBtn;
    private JButton saveStaffBtn;
    private NewStaffForm newStaffForm;
    private JPanel wrapListStaff;
    
    public DashboardAdminController() {
        initController();
    }
       
    private void renderListStaff() {
        List<Staff> listStaff = adminService.getAllStaff();
        for (int i = 0; i < listStaff.size(); i++) {
            Staff tmpStaff = listStaff.get(i);
            String name = tmpStaff.getName();
            String dob = tmpStaff.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM yyyy"));
            String phone = tmpStaff.getPhoneNumber();
            String pos = tmpStaff.getPosition();
            EditEvent editEvent = new EditEvent(new EditFunction() );
            DeleteEvent deleteEvent = new DeleteEvent(new DeleteFunc());
            StaffItem tmp = new StaffItem(tmpStaff, name, dob, phone, pos, editEvent, deleteEvent);
            wrapListStaff.add(tmp);
        }
    }
    
    private void re_renderListUI() {
        System.out.println("1");
            wrapListStaff.removeAll();
            System.out.println("2");
            renderListStaff();
            System.out.println("3");
            wrapListStaff.revalidate();
            System.out.println("4");
    }
    
        private boolean validation(String username,String pass, String name, String phone,
         String pos, String day, String month, String year) {

     if (username.compareTo("") == 0 | pass.compareTo("") == 0 | name.compareTo("") == 0
         | phone.compareTo("") == 0 | pos.compareTo("") == 0 | day.compareTo("") == 0
             | month.compareTo("") == 0 | year.compareTo("") == 0) {
             new NotificationController("Fill in all fields, plssss !");
             return false;
         }
     try {
         int tmp_day = Integer.parseInt(day);
          int tmp_month = Integer.parseInt(month);
         int tmp_year = Integer.parseInt(year);

         if (tmp_day < 0 | tmp_day > 31) {
              new NotificationController("Day is invalid !");
              return false;
         }
         if (tmp_month < 0 | tmp_month > 12) {
             new NotificationController("Month is invalid !");
             return false;
         }
           if (tmp_year < 0) {
             new NotificationController("Year is invalid !");
             return false;
         }
     } catch (Exception e) {
         new NotificationController("Input is invalid !");
          e.printStackTrace();
          return false;
     }
     return true;
 }

        void addStaff() {
        String username =  newStaffForm.getUserNameField().getText();
        String pass =  newStaffForm.getPasswordField().getText();
        String name =  newStaffForm.getNameField().getText();
        String phone =  newStaffForm.getPhoneField().getText();
        String pos =  newStaffForm.getPosField().getText();
        String day =  newStaffForm.getDayField().getText();
        String month =  newStaffForm.getMonthField().getText();
        String year =  newStaffForm.getYearField().getText();
            if (validation(username, pass, name, phone, pos, day, month, year)) {
                LocalDate dob = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month ), Integer.parseInt(day));
                Staff staff = new Staff( name, dob, phone, pos);
                staff.setAccount(new Account(username, pass, "staff"));
                adminService.saveStaff(staff);
                new NotificationController("Add staff successfully !");
                newStaffForm.dispose();
            } 
     }
        
        void updateStaff(NewStaffForm form, Staff editedStaff) {
        String username =  form.getUserNameField().getText();
        String pass =  form.getPasswordField().getText();
        String name =  form.getNameField().getText();
        String phone =  form.getPhoneField().getText();
        String pos =  form.getPosField().getText();
        String day =  form.getDayField().getText();
        String month =  form.getMonthField().getText();
        String year =  form.getYearField().getText();
            if (validation(username, pass, name, phone, pos, day, month, year)) {
                LocalDate dob = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month ), Integer.parseInt(day));
                editedStaff.setAll(name, dob, phone, pos);
//                System.out.println(editedStaff.getId());
                adminService.updateStaff(editedStaff);
                new NotificationController("Update staff successfully !");
                form.dispose();
            } 
        }
    
    private void initController() {
        dashboardAdminUI = new DashboardAdminUI();
        adminService = new AdminService();
        
        //show list staff 
        wrapListStaff = dashboardAdminUI.getContainerListStaff();
        renderListStaff();
        // click new staff btn
        addStaffBtn = dashboardAdminUI.getAddStaffBtn();
        addStaffBtn.addActionListener(new addStaffEvent());
        
        // click save in form 
        newStaffForm = new NewStaffForm();
        saveStaffBtn = newStaffForm.getSaveButton();
        saveStaffBtn.addActionListener(new saveStaff());
        
        dashboardAdminUI.setVisible(true);
    }
    
    private class addStaffEvent implements ActionListener {        
        @Override
        public void actionPerformed(ActionEvent e) {
            newStaffForm.setVisible(true);
        }
    }
    
    private class saveStaff implements  ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addStaff();
           re_renderListUI();
        }
        
    }
    
    public class EditFunction {
        public void execute(NewStaffForm form, Staff editedStaff) {
            updateStaff(form, editedStaff);
            re_renderListUI();
        }
    }
    
    public class DeleteFunc {
        public void execute(Staff staff) {
            adminService.deleteStaff(staff);
            re_renderListUI();
        }
    }
}

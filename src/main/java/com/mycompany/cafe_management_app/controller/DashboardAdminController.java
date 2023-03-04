/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.DashboardAdminUI;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.DishForm;
import com.mycompany.cafe_management_app.util.callback.DeleteEvent;
import com.mycompany.cafe_management_app.util.callback.EditEvent;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.NewStaffForm;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.StaffItem;
import com.mycompany.cafe_management_app.ui.MenuItem;
import com.mycompany.cafe_management_app.ui.DetailsDish;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author namho
 */
public class DashboardAdminController {
    private DashboardAdminUI dashboardAdminUI;
    private AdminService adminService;
    private JButton addStaffBtn;
    private JButton addDishBtn;
    private JButton saveStaffBtn;
    private NewStaffForm newStaffForm;
    private JPanel wrapListStaff;    
    private JPanel wrapListDish;

    
    public DashboardAdminController() {
        initController();
    }
    
    private void initController() {
        dashboardAdminUI = new DashboardAdminUI();
        adminService = new AdminService();
       
        // Staff -----------------------------------------------------------

        //show list staff 
        wrapListStaff = dashboardAdminUI.getContainerListStaff();
//        renderListStaff(); issu of database
        // click new staff btn
        addStaffBtn = dashboardAdminUI.getAddStaffBtn();
        addStaffBtn.addActionListener(new addStaffEvent());
        
        // click save in form 
        newStaffForm = new NewStaffForm();
        saveStaffBtn = newStaffForm.getSaveButton();
        saveStaffBtn.addActionListener(new saveStaff());
        
        // Menu -----------------------------------------------------------
        wrapListDish = dashboardAdminUI.getListDishContainer();
        renderListMenu();
        addDishBtn = dashboardAdminUI.getAddDishBtn();
        addDishBtn.addActionListener(new addDishListener());        
        
        dashboardAdminUI.setVisible(true);
    }
       
//    Staff method ----------------------------------------------------------------------------------------------------------
    private void renderListStaff() {
        List<Staff> listStaff = adminService.getAllStaff();
        for (int i = 0; i < listStaff.size(); i++) {
            Staff tmpStaff = listStaff.get(i);
            String name = tmpStaff.getName();
            String dob = tmpStaff.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM yyyy"));
            String phone = tmpStaff.getPhoneNumber();
            String pos = tmpStaff.getPosition();
            EditEvent editEvent = new EditEvent(new EditFunction());
            DeleteEvent deleteEvent = new DeleteEvent(new DeleteFunc());
            StaffItem tmp = new StaffItem(tmpStaff, name, dob, phone, pos, editEvent, deleteEvent);
            wrapListStaff.add(tmp);
        }
    }
    
    private void re_renderListUI() {
            wrapListStaff.removeAll();
            renderListStaff();
            dashboardAdminUI.revalidate();
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
    
    private class addStaffEvent implements ActionListener {        
        @Override
        public void actionPerformed(ActionEvent e) {
            newStaffForm.setVisible(true);
        }
    }
   
    
    //    Menu method ----------------------------------------------------------------------------------------------------------
    private void renderListMenu() {
        ArrayList<String> listDish = new ArrayList<String>();
        listDish.add("Espresso");
        listDish.add("Latte");
        for (String dish : listDish) {
            wrapListDish.add(new MenuItem(dish, new DetailsDishFunction(), new EditDishFunction(), Boolean.FALSE));
        }
    }
    
   public class DetailsDishFunction {
       public void showDetails() {
           new DetailsDish().setVisible(true);
       }
   }
   
     public class EditDishFunction {
       public void execute() {
           new DishForm().setVisible(true);
       }
   }
   
   private class addDishListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new DishForm().setVisible(true);
        }
   }
}

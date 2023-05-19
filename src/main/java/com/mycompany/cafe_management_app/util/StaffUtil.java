/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.util;

import com.mycompany.cafe_management_app.controller.DashboardAdminController;
import com.mycompany.cafe_management_app.controller.NotificationController;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.NewStaffForm;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.StaffItem;
import com.mycompany.cafe_management_app.util.callback.DeleteEvent;
import com.mycompany.cafe_management_app.util.callback.EditEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author namho
 */
public class StaffUtil {
    public boolean validation(String username,String pass, String name, String phone,
         String pos, String day, String month, String year) {

     if (username.equals("")| pass.equals("")| name.equals("")
         | phone.equals("")| pos.equals("")| day.equals("")
             | month.equals("")| year.equals("")) {
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
    
    public void updateStaff(NewStaffForm form, Staff editedStaff, AdminService adminService, ErrorUtil errorUtil) {
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
            adminService.updateStaff(editedStaff);
            if (errorUtil.getErrorCode() == 0) {
                new NotificationController("Update staff successfully !");
            } else {
                new NotificationController("Update staff failed !");
            }

            form.dispose();
        } 
    }
}

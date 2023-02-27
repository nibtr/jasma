/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.dao.AccountDao;
import com.mycompany.cafe_management_app.dao.TimekeepingDao;
import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;
import com.mycompany.cafe_management_app.service.LoginService;
import com.mycompany.cafe_management_app.service.StaffService;
import com.mycompany.cafe_management_app.util.UserSession;
import java.time.LocalDateTime;

/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
//        Staff staff = new Staff(
//                "Test Staff4",
//                LocalDate.of(2002, 6, 1),
//                "0123456789",
//                "staff"
//        );
//        Account account = new Account("test_staff4", "1234", "staff");
//        staff.setAccount(account);
//        
//        AdminService adminService = new AdminService();
////        adminService.saveStaff(staff);
//        
        LoginService loginService = new LoginService();
        loginService.authenticate("test_staff4", "1234");
//        
        StaffService staffService = new StaffService();
//        staffService.checkIn();
        
//        staffService.checkOut();
        System.out.println(new TimekeepingDao().getLatestOf(UserSession.getInstance().getUserID()).getTotalTime());

     }
}

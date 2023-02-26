/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;
import java.time.LocalDate;

/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        Staff staff = new Staff(
                "Hieu",
                LocalDate.of(2002, 6, 1),
                "0777058016",
                "admin"
        );
        Account account = new Account("admin", "admin", "admin");
        staff.setAccount(account);
        
        AdminService adminService = new AdminService();
//        adminService.saveStaff(staff);
        
        System.out.println(adminService.getAllStaff().get(0).getAccount().getUsername());

    }
}

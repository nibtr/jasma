/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.controller.DashboardAdminController;
import com.mycompany.cafe_management_app.controller.DashboardStaffController;
import com.mycompany.cafe_management_app.dao.AccountDao;
import com.mycompany.cafe_management_app.dao.TimekeepingDao;
import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;
import com.mycompany.cafe_management_app.service.LoginService;
import com.mycompany.cafe_management_app.service.StaffService;
import com.mycompany.cafe_management_app.ui.LoginUI;
import com.mycompany.cafe_management_app.controller.LoginController;
import com.mycompany.cafe_management_app.dao.DishDao;
import com.mycompany.cafe_management_app.dao.DishDetailDao;
import com.mycompany.cafe_management_app.dao.StaffDao;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        HibernateConfig.getSessionFactory();
        // StaffDao staffDao = new StaffDao();
        // if (staffDao.getAll() == null) {
        //     Staff staff = new Staff("Hieu", LocalDate.of(2002, 6, 1), "0123456789");
        //     Account acc = new Account("admin", "admin", "admin");
        //     staff.setAccount(acc);
        //     staffDao.save(staff);
        // }

        // Staff staff = new Staff("Trung", LocalDate.of(2002, 6, 1), "0123456789", "staff");
        // Account acc = new Account("staff", "staff", "staff");
        // staff.setAccount(acc);
        // staffDao.save(staff);

        LoginController loginController = new LoginController();
        loginController.getLoginUI().setVisible(true);
       

        // DashboardStaffController staffController = new DashboardStaffController();
        // staffController.getDashboardStaffUI().setVisible(true);
    }
}

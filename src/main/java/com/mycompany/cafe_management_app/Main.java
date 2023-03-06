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
import com.mycompany.cafe_management_app.dao.BillDao;
import com.mycompany.cafe_management_app.dao.BillDetailDao;
import com.mycompany.cafe_management_app.dao.DishDao;
import com.mycompany.cafe_management_app.dao.DishDetailDao;
import com.mycompany.cafe_management_app.dao.StaffDao;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.BillDetail;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;
import com.mycompany.cafe_management_app.util.ErrorUtil;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        String appID = "my_app_id";
        boolean alreadyRunning;
        try {
            JUnique.acquireLock(appID);
            alreadyRunning = false;
        } catch (AlreadyLockedException e) {
            alreadyRunning = true;
        }
        if (!alreadyRunning) {
            HibernateConfig.getSessionFactory();
            ErrorUtil.getInstance();

            initAdmin();

            // Create Bill
            // BillDao billDao = new BillDao();
            // Bill bill1 = new Bill(LocalDateTime.of(2023, 3, 4, 10, 30));
            // Bill bill2 = new Bill(LocalDateTime.of(2023, 3, 4, 11, 30));
            // Bill bill3 = new Bill(LocalDateTime.of(2023, 3, 4, 12, 30));
            // Bill bill4 = new Bill(LocalDateTime.of(2023, 3, 4, 13, 30));
            // Bill bill5 = new Bill(LocalDateTime.of(2023, 3, 4, 14, 30));
            // Bill bill6 = new Bill(LocalDateTime.of(2023, 3, 4, 15, 30));
            // Bill bill7 = new Bill(LocalDateTime.of(2023, 3, 4, 16, 30));
            // Bill bill8 = new Bill(LocalDateTime.of(2023, 3, 4, 17, 30));
            // Bill bill9 = new Bill(LocalDateTime.of(2023, 3, 4, 18, 30));
            // Bill bill10 = new Bill(LocalDateTime.of(2023, 3, 4, 19, 30));

            // billDao.save(bill1);
            // billDao.save(bill2);
            // billDao.save(bill3);
            // billDao.save(bill4);
            // billDao.save(bill5);
            // billDao.save(bill6);
            // billDao.save(bill7);
            // billDao.save(bill8);
            // billDao.save(bill9);
            // billDao.save(bill10);

             LoginController loginController = new LoginController();
             loginController.getLoginUI().setVisible(true);

//            DashboardStaffController dashboardStaffController = new DashboardStaffController();
//            dashboardStaffController.getDashboardStaffUI().setVisible(true);
        }
    }

    private static void initAdmin() {
        StaffDao staffDao = new StaffDao();

        if (staffDao.getAll().isEmpty()) {
            Staff s = new Staff(
                    "Hieu",
                    LocalDate.of(2002, 6, 1),
                    "012345679",
                    "admin");
            Account a = new Account("admin", "admin", "admin");
            s.setAccount(a);
            staffDao.save(s);

        }
    }
}

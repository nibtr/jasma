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

            // Create Dish and DishDetail
            // DishDao dishDao = new DishDao();
            // DishDetailDao dishDetailDao = new DishDetailDao();

            // Dish dish1 = new Dish("Cafe đen");
            // Dish dish2 = new Dish("Cafe sữa");
            // Dish dish3 = new Dish("Freeze");
            // Dish dish4 = new Dish("Trà sữa");
            // Dish dish5 = new Dish("Trà đào");
            // Dish dish6 = new Dish("Trà chanh");
            // Dish dish7 = new Dish("Trà Sen");

            // dishDao.save(dish1);
            // dishDao.save(dish2);
            // dishDao.save(dish3);
            // dishDao.save(dish4);
            // dishDao.save(dish5);
            // dishDao.save(dish6);
            // dishDao.save(dish7);

            // DishDetail dishDetail1 = new DishDetail("S", 10000.0);
            // dishDetail1.setDish(dish1);
            // DishDetail dishDetail2 = new DishDetail("M", 15000.0);
            // dishDetail2.setDish(dish1);
            // DishDetail dishDetail3 = new DishDetail("L", 20000.0);
            // dishDetail3.setDish(dish1);

            // DishDetail dishDetail4 = new DishDetail("S", 15000.0);
            // dishDetail4.setDish(dish2);
            // DishDetail dishDetail5 = new DishDetail("M", 20000.0);
            // dishDetail5.setDish(dish2);
            // DishDetail dishDetail6 = new DishDetail("L", 25000.0);
            // dishDetail6.setDish(dish2);

            // DishDetail dishDetail7 = new DishDetail("S", 20000.0);
            // dishDetail7.setDish(dish3);
            // DishDetail dishDetail8 = new DishDetail("M", 25000.0);
            // dishDetail8.setDish(dish3);
            // DishDetail dishDetail9 = new DishDetail("L", 30000.0);
            // dishDetail9.setDish(dish3);

            // DishDetail dishDetail10 = new DishDetail("S", 20000.0);
            // dishDetail10.setDish(dish4);
            // DishDetail dishDetail11 = new DishDetail("M", 25000.0);
            // dishDetail11.setDish(dish4);
            // DishDetail dishDetail12 = new DishDetail("L", 30000.0);
            // dishDetail12.setDish(dish4);

            // DishDetail dishDetail13 = new DishDetail("S", 20000.0);
            // dishDetail13.setDish(dish5);
            // DishDetail dishDetail14 = new DishDetail("M", 25000.0);
            // dishDetail14.setDish(dish5);
            // DishDetail dishDetail15 = new DishDetail("L", 30000.0);
            // dishDetail15.setDish(dish5);

            // DishDetail dishDetail16 = new DishDetail("S", 20000.0);
            // dishDetail16.setDish(dish6);
            // DishDetail dishDetail17 = new DishDetail("M", 25000.0);
            // dishDetail17.setDish(dish6);
            // DishDetail dishDetail18 = new DishDetail("L", 30000.0);
            // dishDetail18.setDish(dish6);

            // DishDetail dishDetail19 = new DishDetail("S", 20000.0);
            // dishDetail19.setDish(dish7);
            // DishDetail dishDetail20 = new DishDetail("M", 25000.0);
            // dishDetail20.setDish(dish7);
            // DishDetail dishDetail21 = new DishDetail("L", 30000.0);
            // dishDetail21.setDish(dish7);

            // dishDetailDao.save(dishDetail1);
            // dishDetailDao.save(dishDetail2);
            // dishDetailDao.save(dishDetail3);
            // dishDetailDao.save(dishDetail4);
            // dishDetailDao.save(dishDetail5);
            // dishDetailDao.save(dishDetail6);
            // dishDetailDao.save(dishDetail7);
            // dishDetailDao.save(dishDetail8);
            // dishDetailDao.save(dishDetail9);
            // dishDetailDao.save(dishDetail10);
            // dishDetailDao.save(dishDetail11);
            // dishDetailDao.save(dishDetail12);
            // dishDetailDao.save(dishDetail13);
            // dishDetailDao.save(dishDetail14);
            // dishDetailDao.save(dishDetail15);
            // dishDetailDao.save(dishDetail16);
            // dishDetailDao.save(dishDetail17);
            // dishDetailDao.save(dishDetail18);
            // dishDetailDao.save(dishDetail19);
            // dishDetailDao.save(dishDetail20);
            // dishDetailDao.save(dishDetail21);

            // // Create Bill
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

            // DashboardStaffController dashboardStaffController = new DashboardStaffController();
            // dashboardStaffController.getDashboardStaffUI().setVisible(true);
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

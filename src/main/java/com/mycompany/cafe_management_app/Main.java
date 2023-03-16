/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.controller.LoginController;
import com.mycompany.cafe_management_app.dao.StaffDao;
import com.mycompany.cafe_management_app.service.StaffService;
import com.mycompany.cafe_management_app.util.ClientUtil;
import com.mycompany.cafe_management_app.util.ErrorUtil;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import java.time.LocalDate;

/**
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
//            Init Hibernate and ErrorUtil
            HibernateConfig.getSessionFactory();
            ErrorUtil.getInstance();

//            Init ClientUtil
            ClientUtil.getInstance();

//            Test make transaction async
            StaffService st = new StaffService();
            st.makeTransactionAsync(null, null)
                    .thenApply(res -> {
                        System.out.println(res);
                        return res;
                    });

//            Create admin account if not exist

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
            // Dish dish8 = new Dish("Phindi");
            // Dish dish9 = new Dish("Bánh mì");
            // Dish dish10 = new Dish("Món 10");
            // Dish dish11 = new Dish("Món 11");
            // Dish dish12 = new Dish("Món 12");
            // Dish dish13 = new Dish("Món 13");
            // Dish dish14 = new Dish("Món 14");
            // Dish dish15 = new Dish("Món 15");
            // Dish dish16 = new Dish("Món 16");
            // Dish dish17 = new Dish("Món 17");
            // Dish dish18 = new Dish("Món 18");
            // Dish dish19 = new Dish("Món 19");
            // Dish dish20 = new Dish("Món 20");

            // dishDao.save(dish1);
            // dishDao.save(dish2);
            // dishDao.save(dish3);
            // dishDao.save(dish4);
            // dishDao.save(dish5);
            // dishDao.save(dish6);
            // dishDao.save(dish7);
            // dishDao.save(dish8);
            // dishDao.save(dish9);
            // dishDao.save(dish10);
            // dishDao.save(dish11);
            // dishDao.save(dish12);
            // dishDao.save(dish13);
            // dishDao.save(dish14);
            // dishDao.save(dish15);
            // dishDao.save(dish16);
            // dishDao.save(dish17);
            // dishDao.save(dish18);
            // dishDao.save(dish19);
            // dishDao.save(dish20);

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

            // DishDetail dishDetail22 = new DishDetail("S", 20000.0);
            // dishDetail22.setDish(dish8);
            // DishDetail dishDetail23 = new DishDetail("M", 25000.0);
            // dishDetail23.setDish(dish8);
            // DishDetail dishDetail24 = new DishDetail("L", 30000.0);
            // dishDetail24.setDish(dish8);

            // DishDetail dishDetail25 = new DishDetail("S", 20000.0);
            // dishDetail25.setDish(dish9);
            // DishDetail dishDetail26 = new DishDetail("M", 25000.0);
            // dishDetail26.setDish(dish9);
            // DishDetail dishDetail27 = new DishDetail("L", 30000.0);
            // dishDetail27.setDish(dish9);

            // DishDetail dishDetail28 = new DishDetail("S", 20000.0);
            // dishDetail28.setDish(dish10);
            // DishDetail dishDetail29 = new DishDetail("M", 25000.0);
            // dishDetail29.setDish(dish10);
            // DishDetail dishDetail30 = new DishDetail("L", 30000.0);
            // dishDetail30.setDish(dish10);

            // DishDetail dishDetail31 = new DishDetail("S", 20000.0);
            // dishDetail31.setDish(dish11);
            // DishDetail dishDetail32 = new DishDetail("M", 25000.0);
            // dishDetail32.setDish(dish11);
            // DishDetail dishDetail33 = new DishDetail("L", 30000.0);
            // dishDetail33.setDish(dish11);

            // DishDetail dishDetail34 = new DishDetail("S", 20000.0);
            // dishDetail34.setDish(dish12);
            // DishDetail dishDetail35 = new DishDetail("M", 25000.0);
            // dishDetail35.setDish(dish12);
            // DishDetail dishDetail36 = new DishDetail("L", 30000.0);
            // dishDetail36.setDish(dish12);

            // DishDetail dishDetail37 = new DishDetail("S", 20000.0);
            // dishDetail37.setDish(dish13);
            // DishDetail dishDetail38 = new DishDetail("M", 25000.0);
            // dishDetail38.setDish(dish13);
            // DishDetail dishDetail39 = new DishDetail("L", 30000.0);
            // dishDetail39.setDish(dish13);

            // DishDetail dishDetail40 = new DishDetail("S", 20000.0);
            // dishDetail40.setDish(dish14);
            // DishDetail dishDetail41 = new DishDetail("M", 25000.0);
            // dishDetail41.setDish(dish14);
            // DishDetail dishDetail42 = new DishDetail("L", 30000.0);
            // dishDetail42.setDish(dish14);

            // DishDetail dishDetail43 = new DishDetail("S", 20000.0);
            // dishDetail43.setDish(dish15);
            // DishDetail dishDetail44 = new DishDetail("M", 25000.0);
            // dishDetail44.setDish(dish15);
            // DishDetail dishDetail45 = new DishDetail("L", 30000.0);
            // dishDetail45.setDish(dish15);

            // DishDetail dishDetail46 = new DishDetail("S", 20000.0);
            // dishDetail46.setDish(dish16);
            // DishDetail dishDetail47 = new DishDetail("M", 25000.0);
            // dishDetail47.setDish(dish16);
            // DishDetail dishDetail48 = new DishDetail("L", 30000.0);
            // dishDetail48.setDish(dish16);

            // DishDetail dishDetail49 = new DishDetail("S", 20000.0);
            // dishDetail49.setDish(dish17);
            // DishDetail dishDetail50 = new DishDetail("M", 25000.0);
            // dishDetail50.setDish(dish17);
            // DishDetail dishDetail51 = new DishDetail("L", 30000.0);
            // dishDetail51.setDish(dish17);

            // DishDetail dishDetail52 = new DishDetail("S", 20000.0);
            // dishDetail52.setDish(dish18);
            // DishDetail dishDetail53 = new DishDetail("M", 25000.0);
            // dishDetail53.setDish(dish18);
            // DishDetail dishDetail54 = new DishDetail("L", 30000.0);
            // dishDetail54.setDish(dish18);

            // DishDetail dishDetail55 = new DishDetail("S", 20000.0);
            // dishDetail55.setDish(dish19);
            // DishDetail dishDetail56 = new DishDetail("M", 25000.0);
            // dishDetail56.setDish(dish19);
            // DishDetail dishDetail57 = new DishDetail("L", 30000.0);
            // dishDetail57.setDish(dish19);

            // DishDetail dishDetail58 = new DishDetail("S", 20000.0);
            // dishDetail58.setDish(dish20);
            // DishDetail dishDetail59 = new DishDetail("M", 25000.0);
            // dishDetail59.setDish(dish20);
            // DishDetail dishDetail60 = new DishDetail("L", 30000.0);
            // dishDetail60.setDish(dish20);

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
            // dishDetailDao.save(dishDetail22);
            // dishDetailDao.save(dishDetail23);
            // dishDetailDao.save(dishDetail24);
            // dishDetailDao.save(dishDetail25);
            // dishDetailDao.save(dishDetail26);
            // dishDetailDao.save(dishDetail27);
            // dishDetailDao.save(dishDetail28);
            // dishDetailDao.save(dishDetail29);
            // dishDetailDao.save(dishDetail30);
            // dishDetailDao.save(dishDetail31);
            // dishDetailDao.save(dishDetail32);
            // dishDetailDao.save(dishDetail33);
            // dishDetailDao.save(dishDetail34);
            // dishDetailDao.save(dishDetail35);
            // dishDetailDao.save(dishDetail36);
            // dishDetailDao.save(dishDetail37);
            // dishDetailDao.save(dishDetail38);
            // dishDetailDao.save(dishDetail39);
            // dishDetailDao.save(dishDetail40);
            // dishDetailDao.save(dishDetail41);
            // dishDetailDao.save(dishDetail42);
            // dishDetailDao.save(dishDetail43);
            // dishDetailDao.save(dishDetail44);
            // dishDetailDao.save(dishDetail45);
            // dishDetailDao.save(dishDetail46);
            // dishDetailDao.save(dishDetail47);
            // dishDetailDao.save(dishDetail48);
            // dishDetailDao.save(dishDetail49);
            // dishDetailDao.save(dishDetail50);
            // dishDetailDao.save(dishDetail51);
            // dishDetailDao.save(dishDetail52);
            // dishDetailDao.save(dishDetail53);
            // dishDetailDao.save(dishDetail54);
            // dishDetailDao.save(dishDetail55);
            // dishDetailDao.save(dishDetail56);
            // dishDetailDao.save(dishDetail57);
            // dishDetailDao.save(dishDetail58);
            // dishDetailDao.save(dishDetail59);
            // dishDetailDao.save(dishDetail60);

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

//            List<DishDetail> list = new DishDetailDao().getByDishName("Cafe");
//            System.out.println(list.get(0).getDish().getName());

//            Show login UI
            LoginController loginController = new LoginController();
            loginController.getLoginUI().setVisible(true);

            // DashboardStaffController dashboardStaffController = new
            // DashboardStaffController();
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

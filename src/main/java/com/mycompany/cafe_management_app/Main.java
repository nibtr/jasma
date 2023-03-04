/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.controller.DashboardAdminController;
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
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.BillDetail;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        HibernateConfig.getSessionFactory();
        
        Dish dish1 = new Dish("cafe den");    
        dish1.addDetail(new DishDetail("M", 35000.0));
        dish1.addDetail(new DishDetail("S", 40000.0));
        dish1.addDetail(new DishDetail("L", 45000.0));
        
        DishDao dishDao = new DishDao();
//        dishDao.save(dish1);
        
        Bill bill = new Bill(LocalDateTime.now());

        DishDetailDao ddDao = new DishDetailDao();
        
        BillDetail bdt1 = new BillDetail(ddDao.getByDishName("cafe den").get(0), (long) 2);
        BillDetail bdt2 = new BillDetail(ddDao.getByDishName("cafe den").get(1), (long) 2);
//        BillDetailDao bdtDao = new BillDetailDao();
//        bdtDao.save(bdt1);
//        bdtDao.save(bdt2);

        bill.addBillDetail(bdt1);
        bill.addBillDetail(bdt2);
        
        StaffService stSerivce = new StaffService();
        stSerivce.createBill(bill, 200000.0);
     }
}

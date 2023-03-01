/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.controller.LoginController;
import com.mycompany.cafe_management_app.dao.DishDao;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;
import java.util.List;


/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        HibernateConfig.getSessionFactory();
        
//        LoginController loginController = new LoginController();
//        loginController.getLoginUI().setVisible(true);

        Dish dish = new Dish("cafe sua");
        dish.addDetail(new DishDetail("S", (double) 35000));
        dish.addDetail(new DishDetail("M", (double) 40000));
        dish.addDetail(new DishDetail("L", (double) 45000));

//        Dish dish2 = new Dish("cafe sua");
//        details = dish2.getDetails();
//        details.add(new DishDetail("S", (double) 30000));
//        details.add(new DishDetail("M", (double) 35000));
//        details.add(new DishDetail("L", (double) 40000));
//        dish2.setDetails(details);
        
        DishDao dishDao = new DishDao();
        dishDao.save(dish);
//        dishDao.save(dish2);

     }
}

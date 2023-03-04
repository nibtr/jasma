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
import com.mycompany.cafe_management_app.util.ErrorUtil;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        HibernateConfig.getSessionFactory();
        ErrorUtil.getInstance();
        
        String appID = "my_app_id";
        boolean alreadyRunning;
	try {
            JUnique.acquireLock(appID);
            alreadyRunning = false;
	} catch (AlreadyLockedException e) {
            alreadyRunning = true;
	}
	if (!alreadyRunning) {
            LoginController loginController = new LoginController();
            loginController.getLoginUI().setVisible(true);
	}

     }
}

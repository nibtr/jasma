/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.controller.LoginController;


/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        HibernateConfig.getSessionFactory();
        
        LoginController loginController = new LoginController();
        loginController.getLoginUI().setVisible(true);

     }
}

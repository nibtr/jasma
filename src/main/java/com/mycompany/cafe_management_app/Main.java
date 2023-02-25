/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.DashboardAdminUI;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
//        Test JDBC connection
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        DashboardAdminUI adminUI = new DashboardAdminUI();
        adminUI.setVisible(true);
    }
}

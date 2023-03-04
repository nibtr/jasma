/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.config;

import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.BillDetail;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.model.Revenue;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Hieu
 */
public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

//                Configure the properties
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/cafe_management");
                configuration.setProperty("hibernate.connection.username", "root");
                configuration.setProperty("hibernate.connection.password", "hello321");
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                configuration.setProperty("hibernate.current_session_context_class", "thread");

//                Add new POJOs (Model) here
                configuration.addAnnotatedClass(Account.class);
                configuration.addAnnotatedClass(Staff.class);
                configuration.addAnnotatedClass(Dish.class);
                configuration.addAnnotatedClass(Timekeeping.class);
                configuration.addAnnotatedClass(DishDetail.class);
                configuration.addAnnotatedClass(Bill.class);
                configuration.addAnnotatedClass(BillDetail.class);
                configuration.addAnnotatedClass(Revenue.class);


//                Build the service factory
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}

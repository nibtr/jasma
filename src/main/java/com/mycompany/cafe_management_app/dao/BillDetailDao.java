/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.BillDetail;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Hieu
 */
public class BillDetailDao implements DaoInterface<BillDetail>{

    @Override
    public List<BillDetail> getAll() {
//        Not necessary
        return new ArrayList<>();
    }

    @Override
    public void save(BillDetail t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            session.persist(t);
            tx.commit();
            
        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
           
            e.printStackTrace();
        } finally {
            session.close();
        }  
    }

    @Override
    public void update(BillDetail t) {
        
    }

    @Override
    public void delele(BillDetail t) {
    }
    
}

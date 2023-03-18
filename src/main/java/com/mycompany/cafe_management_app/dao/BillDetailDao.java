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
import org.hibernate.query.Query;

/**
 *
 * @author Hieu
 */
public class BillDetailDao implements DaoInterface<BillDetail>{


    public List<BillDetail> getByBillID(Long id) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<BillDetail> list = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM BillDetail t where bill.id = :id ORDER BY price ASC" );
            query.setParameter("id", id);
            list = query.getResultList();
//            init the bill
            for (BillDetail billDetail : list) {
                billDetail.getBill().getTotalPrice();
            }
//            init the dish
            for (BillDetail billDetail : list) {
                billDetail.getDishDetail().getDish().getName();
            }
            tx.commit();

        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }

            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<BillDetail> getAll() {
        return null;
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
//            session.close();
        }  
    }

    @Override
    public void update(BillDetail t) {
        
    }

    @Override
    public void delete(BillDetail t) {
    }
    
}

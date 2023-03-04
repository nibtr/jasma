/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.util.ErrorUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Hieu
 */
public class BillDao implements DaoInterface<Bill>{

    @Override
    public List<Bill> getAll() {
        List<Bill> billList = new ArrayList<>();
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
  
        try {
            tx = session.beginTransaction();   
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Bill> criteria = builder.createQuery(Bill.class);
            criteria.select(criteria.from(Bill.class));
            billList = session.createQuery(criteria).getResultList();
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);

        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            e.printStackTrace();
        } finally {
            session.close();
        }  

        return billList;
    }

    @Override
    public void save(Bill t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            session.persist(t);
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            
        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
           
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            e.printStackTrace();
        } finally {
            session.close();
        }  
    }

    @Override
    public void update(Bill t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            session.update(t);
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            
        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
           
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            e.printStackTrace();
        } finally {
            session.close();
        } 
    }

    @Override
    public void delete(Bill t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            session.delete(t);
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            
        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
           
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            e.printStackTrace();
        } finally {
            session.close();
        } 
    }
    
}

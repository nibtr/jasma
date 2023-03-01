/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.Timekeeping;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Hieu
 */
public class TimekeepingDao implements DaoInterface<Timekeeping>{

    @Override
    public List<Timekeeping> getAll() {
//        Not necessary
        return null;
    }
    
    public List<Timekeeping> getListOf(Long staffID) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Timekeeping> list = null;

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Timekeeping t WHERE staff.id= :staffID ORDER BY checkinTime DESC");
            query.setParameter("staffID", staffID);
            list = query.getResultList();
            tx.commit();
         
        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
           
            e.printStackTrace();

        } finally {
            session.close();
        } 
        
        return list;
    }
    
    public Timekeeping getLatestOf(Long staffID) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        Timekeeping latest = null;

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Timekeeping t WHERE staff.id= :staffID ORDER BY checkinTime DESC");
            query.setParameter("staffID", staffID);
            query.setMaxResults(1);
            latest = (Timekeeping) query.uniqueResult();
            tx.commit();
         
        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
           
            e.printStackTrace();

        } finally {
            session.close();
        } 
        
        return latest;
    }

    @Override
    public void save(Timekeeping t) {
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
    public void update(Timekeeping t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            session.update(t);
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
    public void delele(Timekeeping t) {
//        Not necessary
    }
    
}

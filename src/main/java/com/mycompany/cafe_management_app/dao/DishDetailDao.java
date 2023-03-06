/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.DishDetail;
import com.mycompany.cafe_management_app.util.ErrorUtil;
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
public class DishDetailDao implements DaoInterface<DishDetail>{

    @Override
    public List<DishDetail> getAll() {
//        Not necessary
        return new ArrayList<>();
    }
    
    public List<DishDetail> getByDishID(Long dishID) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<DishDetail> list = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM DishDetail t where dish.id = :dishID ORDER BY price ASC" );
            query.setParameter("dishID", dishID);
            list = query.getResultList();      
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return list;
    }
    
    public List<DishDetail> getByDishName(String dishName) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<DishDetail> list = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM DishDetail t where dish.name = :dishName ORDER BY price ASC" );
            query.setParameter("dishName", dishName);
            list = query.getResultList();      
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return list;
    }

    @Override
    public void save(DishDetail t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(t);
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            ErrorUtil.getInstance().setMessage("Saved successfully");
        } catch (HibernateException e) {
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
    public void update(DishDetail t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(t);
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            ErrorUtil.getInstance().setMessage("Updated successfully");
        } catch (HibernateException e) {
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
    public void delete(DishDetail t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(t);
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            ErrorUtil.getInstance().setMessage("Deleted successfully");
        } catch (HibernateException e) {
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

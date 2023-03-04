/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.util.PasswordUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
public class StaffDao implements DaoInterface<Staff>{
    public List<Staff> getByName(String name) {
        String hql = "FROM Staff a WHERE a.name = :name";
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            List<Staff> staffs = query.list();
            tx.commit(); 
            
            return staffs.isEmpty() ? null : staffs;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            
            return null;
        } finally {
            session.close();
        } 
    }
    
    @Override
    public List<Staff> getAll() {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
  
        try {
            tx = session.beginTransaction();   
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Staff> criteria = builder.createQuery(Staff.class);
            criteria.select(criteria.from(Staff.class));
            List<Staff> accountList = session.createQuery(criteria).getResultList();
            tx.commit();
            
            return accountList.isEmpty() ? null : accountList;

        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            
            e.printStackTrace();
            
            return null;
        } finally {
            session.close();
        }  
    }
    
    public Staff getByID(Long id) {
        Staff staff = null;
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            staff = session.get(Staff.class, id);
            tx.commit();
        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
           
            e.printStackTrace();
        } finally {
            session.close();
        }  
        
        return staff;
    }
    
//    public Staff getByAccountID(Long id) {
//        
//    }

    @Override
    public void save(Staff t) {
        Account account = t.getAccount();
        String hashedPassword = PasswordUtil.hash(account.getPassword());
        account.setPassword(hashedPassword);
        t.setAccount(account);
        
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
    public void update(Staff t) {
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
    public void delele(Staff t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            session.delete(t);
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
    
}

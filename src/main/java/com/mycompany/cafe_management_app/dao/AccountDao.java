/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.Account;
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
public class AccountDao implements DaoInterface<Account>{
    
    public Account getById(Long id) {
        Account account = null;
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            account = session.get(Account.class, id);
            tx.commit();
        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
           
            e.printStackTrace();
        } finally {
            session.close();
        }  
        
        return account;
    }
    
    public Account getAccountByUsername(String username) {
        String hql = "FROM Account a WHERE a.username = :username";
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            List<Account> accounts = query.list();
            tx.commit(); 
            
            return accounts.isEmpty() ? null : accounts.get(0);
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
    public List<Account> getAll() {
        List<Account> accountList = new ArrayList<>();
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
  
        try {
            tx = session.beginTransaction();   
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> criteria = builder.createQuery(Account.class);
            criteria.select(criteria.from(Account.class));
            accountList = session.createQuery(criteria).getResultList();
            tx.commit();

        } catch(HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            
            e.printStackTrace();
        } finally {
            session.close();
        }  

        return accountList;
    }

    @Override
    public void save(Account t) {
        String hashedPassword = PasswordUtil.hash(t.getPassword());
        t.setPassword(hashedPassword);
        
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
    public void update(Account t) {
//        No update functionality for account
    }

    @Override
    public void delele(Account t) {
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

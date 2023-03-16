/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.Salary;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.mycompany.cafe_management_app.util.ErrorUtil;
import java.util.ArrayList;
import org.hibernate.query.Query;

/**
 *
 * @author Legion
 */
public class SalaryDao implements DaoInterface<Salary> {

    @Override
    public List<Salary> getAll() {
        return new ArrayList<>();
    }

    @Override
    public void save(Salary t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Salary t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    @Override
    public void delete(Salary t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.remove(t);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Salary> setAmount(int month) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Salary> list = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("SELECT SUM(payment) FROM Timekeeping WHERE MONTH(checkin_time) :=month");
            query.setParameter("amount", month);
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

    

}

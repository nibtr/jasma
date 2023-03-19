/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.util.ErrorUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Legion
 */
public class DishDao implements DaoInterface<Dish> {

    @Override
    public List<Dish> getAll() {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Dish> dishes = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Dish> criteria = builder.createQuery(Dish.class);
            Root<Dish> root = criteria.from(Dish.class);
            criteria.select(root);
            dishes = session.createQuery(criteria).getResultList();
//            Initialize the detail list of the dish
            for (Dish dish : dishes) {
                dish.getDetails().size();
            }
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
//            session.close();
        }

        return dishes;
    }

    public List<Dish> searchByName(String name) {
        String hql = "FROM Dish a WHERE a.name like :search";
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Dish> dishes = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("search", "%" + name + "%");
            dishes = query.list();
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
//            session.close();
        }

        return dishes;
    }

    public Dish getByName(String name) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        Dish dish;

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Dish t WHERE t.name = :name");
            query.setParameter("name", name);
            dish = (Dish) query.uniqueResult();
//            Initialize the detail list of the dish
            dish.getDetails().size();         
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            e.printStackTrace();

            return null;
        } finally {
//            session.close();
        }
        return dish;
    }
    
    public Dish getByID(Long id) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        Dish dish;

        try {
            tx = session.beginTransaction();
            dish = session.get(Dish.class, id);
//            Initialize the detail list of the dish
            dish.getDetails().size();
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            e.printStackTrace();

            return null;
        } finally {
//            session.close();
        }
        return dish;
    }

    @Override
    public void save(Dish t) {
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
//            session.close();
        }
    }

    @Override
    public void update(Dish t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(t);
            tx.commit();
            
            ErrorUtil.getInstance().setErrorCode(0);
            ErrorUtil.getInstance().setMessage("Updated successfullly");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            
            ErrorUtil.getInstance().setErrorCode(1);
            ErrorUtil.getInstance().setMessage("Something went wrong");
            e.printStackTrace();
        } finally {
//            session.close();
        }
    }

    @Override
    public void delete(Dish t) {
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
//            session.close();
        }
    }

}

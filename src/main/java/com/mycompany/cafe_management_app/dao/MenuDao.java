/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.model.Menu;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Legion
 */
public class MenuDao implements DaoInterface<Menu> {

    @Override
    public List<Menu> getAll() {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        List<Menu> menus = null;

        try {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Menu> criteria = builder.createQuery(Menu.class);
            Root<Menu> root = criteria.from(Menu.class);
            criteria.select(root);
            menus = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return menus;
    }

    @Override
    public void save(Menu t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(t);
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
    public void update(Menu t) {
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
    public void delele(Menu t) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(t);
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

    public Menu findByName(String name) {
        Session session = HibernateConfig.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        Menu menu = null;

        try {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Menu> criteria = builder.createQuery(Menu.class);
            Root<Menu> root = criteria.from(Menu.class);
            criteria.select(root).where(builder.equal(root.get("name"), name));
            menu = session.createQuery(criteria).getSingleResult();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();

            return null;
        } finally {
            session.close();
        }
        return menu;
    }

}

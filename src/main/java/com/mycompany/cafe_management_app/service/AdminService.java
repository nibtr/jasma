/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;

import com.mycompany.cafe_management_app.dao.*;
import com.mycompany.cafe_management_app.model.*;
import com.mycompany.cafe_management_app.util.ClientUtil;
import com.mycompany.cafe_management_app.util.JSONObjUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hieu
 */
public class AdminService {
    private final StaffDao staffDao;
    private final DishDao dishDao;
    private final BillDao billDao;
    private final SalaryDao salaryDao;
    private final DishDetailDao dishDetailDao;
    private final RevenueDao revenueDao;

    public AdminService() {
        staffDao = new StaffDao();
        dishDao = new DishDao();
        billDao = new BillDao();
        salaryDao = new SalaryDao();
        dishDetailDao = new DishDetailDao();
        revenueDao = new RevenueDao();
    }

    public List<Staff> getAllStaff() {
        return staffDao.getAll();
    }

    public List<Staff> searchStaffByName(String name) {
        return staffDao.searchByName(name);
    }

    public void saveStaff(Staff t) {
        staffDao.save(t);
    }

    public void updateStaff(Staff t) {
        staffDao.update(t);
    }

    public void deleteStaff(Staff t) {
        staffDao.delete(t);
    }

    public List<Dish> getAllDish() {
        return dishDao.getAll();
    }

    public List<Dish> searchDishByName(String name) {
        return dishDao.searchByName(name);
    }

    public Dish getDishByID(Long id) {
        return dishDao.getByID(id);
    }

    public void saveDish(Dish t) {
        dishDao.save(t);
    }

    private void typeOf(Long s) {
        System.out.println("Is Long");
    }

    public void updateDish(Dish t, List<DishDetail> newList) {
        dishDao.update(t);

        for (DishDetail d : newList) {
            if (d.getId() == null) {
                d.setDish(t);
                dishDetailDao.save(d);
            } else {
                dishDetailDao.update(d);
            }
        }
    }

    public void updateDish(Dish t) {
        dishDao.update(t);
    }

    public void deleteDish(Dish t) {
        dishDao.delete(t);
    }

    public List<DishDetail> getDetailsOf(Long id) { return dishDetailDao.getByDishID(id); }

    public void updateDishDetail(DishDetail t) { dishDetailDao.update(t); }

    public void deleteDishDetail(DishDetail t) { dishDetailDao.delete(t); }

    public List<Bill> getAllBills() {
        return billDao.getAll();
    }

    public List<Revenue> getAllRevenues() {
        return revenueDao.getAll();
    }

}

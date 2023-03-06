/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;

import com.mycompany.cafe_management_app.dao.*;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;
import com.mycompany.cafe_management_app.model.Staff;

import java.util.List;

/**
 * @author Hieu
 */
public class AdminService {
    private final StaffDao staffDao;
    private final DishDao dishDao;
    private final BillDao billDao;
    private final SalaryDao salaryDao;
    private final DishDetailDao dishDetailDao;

    public AdminService() {
        staffDao = new StaffDao();
        dishDao = new DishDao();
        billDao = new BillDao();
        salaryDao = new SalaryDao();
        dishDetailDao = new DishDetailDao();
    }

    public List<Staff> getAllStaff() {
        return staffDao.getAll();
    }

    public List<Staff> getStaffsByName(String name) {
        return staffDao.getByName(name);
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

    public Dish getDishByName(String name) {
        return dishDao.getByName(name);
    }

    public Dish getDishByID(Long id) {
        return dishDao.getByID(id);
    }

    public void saveDish(Dish t) {
        dishDao.save(t);
    }

    public void updateDish(Dish t) {
        dishDao.update(t);
    }

    public void deleteDish(Dish t) {
        dishDao.delete(t);
    }

    public List<DishDetail> getDetailsOf(Long id) { return dishDetailDao.getByDishID(id); }

    public void updateDishDetail(DishDetail t) { dishDetailDao.update(t); }

    public List<Bill> getAllBills() {
        return billDao.getAll();
    }
}

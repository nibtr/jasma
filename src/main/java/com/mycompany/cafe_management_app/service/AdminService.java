/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;

import com.mycompany.cafe_management_app.dao.*;
import com.mycompany.cafe_management_app.model.*;

import java.time.LocalDate;
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

    public void updateDish(Dish t, List<DishDetail> newList) {
        System.out.println("New list:");
        for (DishDetail dt: newList) {
            System.out.println(dt.getId() + " " + dt.getSize() + " " + dt.getPrice() + " " + dt.getDish().getId());
        }

//        Get the list from database
        List<DishDetail> dbList = dishDetailDao.getByDishID(t.getId());

//        Put the list from database into a map
        HashMap<Long, DishDetail> currentMap = new HashMap<>();
        for (DishDetail dt : dbList) {
            currentMap.put(dt.getId(), dt);
        }

        for (DishDetail newDetail : newList) {
            DishDetail currentDetail = currentMap.get(newDetail.getId());
//            System.out.println("Updated:");

            if (currentDetail != null) {
                currentDetail.setPrice(newDetail.getPrice());
                currentDetail.setSize(newDetail.getSize());
                System.out.println(currentDetail.getId() + " " + currentDetail.getSize() + " " + currentDetail.getPrice() + " " + currentDetail.getDish().getId());

                dishDetailDao.update(currentDetail);
            }
            else {
                dishDetailDao.save(newDetail);
            }
        }

//        for (DishDetail currentDetail : dbList) {
//            if (!newList.contains(currentDetail)) {
//                dishDetailDao.delete(currentDetail);
//            }
//        }

        dishDao.update(t);
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

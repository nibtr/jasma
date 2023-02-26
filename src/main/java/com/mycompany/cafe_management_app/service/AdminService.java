/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;

import com.mycompany.cafe_management_app.dao.StaffDao;
import com.mycompany.cafe_management_app.model.Staff;
import java.util.List;

/**
 *
 * @author Hieu
 */
public class AdminService {
    private StaffDao staffDao;
    
    public AdminService() {
        staffDao = new StaffDao();
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
        staffDao.delele(t);
    }
}

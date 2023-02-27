/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;

import com.mycompany.cafe_management_app.dao.StaffDao;
import com.mycompany.cafe_management_app.dao.TimekeepingDao;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.util.UserSession;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Hieu
 */
public class StaffService {
    private TimekeepingDao timekeepingDao;
    private StaffDao staffDao;
    private Staff currentStaff;
    
    public StaffService() {
        timekeepingDao = new TimekeepingDao();
        staffDao = new StaffDao();
        currentStaff = staffDao.getByID(UserSession.getInstance().getUserID());
    }
    
    public void checkIn() {
        Timekeeping t = new Timekeeping();
        t.setStaff(currentStaff);
        timekeepingDao.save(t);
    }
    
    public void checkOut() {
        Long currentStaffID = currentStaff.getId();
        Timekeeping t = timekeepingDao.getLatestOf(currentStaffID);
        
        LocalDateTime currentTime = LocalDateTime.now();
        
//        Calculate total time
        Duration duration = Duration.between(t.getCheckinTime(), currentTime);
        double hours = duration.toMillis() / (double) (1000 * 60 * 60);
        DecimalFormat df = new DecimalFormat("#.##");
        Double formattedHours = Double.parseDouble(df.format(hours));
        t.setTotalTime(formattedHours);
        
//        Calculate payment
        t.setTotalPayment(currentStaff.getHourlyRate() * formattedHours);
        
//        Set checkout time
        t.setCheckoutTime(currentTime);
        
        timekeepingDao.update(t); 
    }
    
    public List<Timekeeping> getAllTimekeeping() {
        return timekeepingDao.getListOf(currentStaff.getId());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;

import com.mycompany.cafe_management_app.dao.TimekeepingDao;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.util.UserSession;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Hieu
 */
public class StaffService {
    private final TimekeepingDao timekeepingDao;
    private final Staff currentStaff;
    
    public StaffService() {
        timekeepingDao = new TimekeepingDao();
        currentStaff = UserSession.getInstance().getStaff();
    }
    
    public void checkIn(LocalDateTime time) {
        Timekeeping t = new Timekeeping(time);
        t.setStaff(currentStaff);
        timekeepingDao.save(t);
    }
    
    public void checkOut(LocalDateTime currentTime) {
        Long currentStaffID = currentStaff.getId();
        Timekeeping t = timekeepingDao.getLatestOf(currentStaffID);      
        
//        Set checkout time
        t.setCheckoutTime(currentTime);
        
//        Calculate total work time
        DecimalFormat df = new DecimalFormat("#.##");
        Duration duration = Duration.between(t.getCheckinTime(), currentTime);
        double hours = duration.toMillis() / (double) (1000 * 60 * 60);
        Double formattedHours = Double.parseDouble(df.format(hours));
        t.setTotalTime(formattedHours);
        
//        Calculate payment
        t.setTotalPayment(currentStaff.getHourlyRate() * formattedHours);
        
        timekeepingDao.update(t); 
    }
    
    public List<Timekeeping> getAllTimekeeping() {
        return timekeepingDao.getListOf(currentStaff.getId());
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;

import com.mycompany.cafe_management_app.dao.BillDao;
import com.mycompany.cafe_management_app.dao.DishDao;
import com.mycompany.cafe_management_app.dao.DishDetailDao;
import com.mycompany.cafe_management_app.dao.StaffDao;
import com.mycompany.cafe_management_app.dao.TimekeepingDao;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.util.ClientUtil;
import com.mycompany.cafe_management_app.util.JSONObjUtil;
import com.mycompany.cafe_management_app.util.UserSession;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Hieu
 */
public class StaffService {
    private final TimekeepingDao timekeepingDao;
    private final DishDao dishDao;
    private final DishDetailDao dishDetailDao;
    private final BillDao billDao;
    private final StaffDao staffDao;
    private final Staff currentStaff;

    public StaffService() {
        timekeepingDao = new TimekeepingDao();
        dishDao = new DishDao();
        dishDetailDao = new DishDetailDao();
        billDao = new BillDao();
        staffDao = new StaffDao();
        currentStaff = UserSession.getInstance().getStaff();
    }

    public void checkIn(LocalDateTime time) {
        Timekeeping t = new Timekeeping(time);
        t.setStaff(currentStaff);
        timekeepingDao.save(t);
        // currentStaff.addTimekeeping(t);
        // staffDao.update(currentStaff);
    }

    public void checkOut(LocalDateTime currentTime) {
        Long currentStaffID = currentStaff.getId();
        Timekeeping t = timekeepingDao.getLatestOf(currentStaffID);

        // Set checkout time
        t.setCheckoutTime(currentTime);

        // Calculate total work time
        DecimalFormat df = new DecimalFormat("#.##");
        Duration duration = Duration.between(t.getCheckinTime(), currentTime);
        double hours = duration.toMillis() / (double) (1000 * 60 * 60);
        Double formattedHours = Double.parseDouble(df.format(hours));
        t.setTotalTime(formattedHours);

        // Calculate payment
        t.setTotalPayment(currentStaff.getHourlyRate() * formattedHours);

        timekeepingDao.update(t);

        // Send CMD=END to server
        ClientUtil.getInstance().sendRequestAsync(JSONObjUtil.toJson(null, "END"));
    }

    public List<Timekeeping> getAllTimekeeping() {
        return timekeepingDao.getListOf(currentStaff.getId());
    }

    public List<Dish> getAllDish() {
        return dishDao.getAll();
    }

    public Dish getDishByID(Long id) {
        return dishDao.getByID(id);
    }

    public Dish getDishByName(String name) {
        return dishDao.getByName(name);
    }

    public List<DishDetail> getDetailsOf(Dish dish) {
        return dishDetailDao.getByDishID(dish.getId());
    }

    public void createBill(Bill t, Double receivedAmount) {
        t.setReceivedAmount(receivedAmount);
        t.setReturnedAmount(receivedAmount - t.getTotalPrice());
        billDao.save(t);
    }

    public List<Bill> getAllBill() {
        return billDao.getAll();
    }

    public CompletableFuture<Object> makeTransactionAsync(Bill bill, String cardNumber) {
        return ClientUtil.getInstance().sendRequestAsync(JSONObjUtil.toJson(null, "TRANSACTION"));
    }

    public String getTotalRevenue() {
        Double totalRevenue = 0.0;
        List<Bill> bills = billDao.getAll();
        for (Bill b : bills) {
            totalRevenue += b.getTotalPrice();
        }
        return String.format("%.2f", totalRevenue);
    }

}

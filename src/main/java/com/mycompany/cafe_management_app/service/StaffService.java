/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;


import com.mycompany.cafe_management_app.config.HibernateConfig;
import com.mycompany.cafe_management_app.dao.BillDao;
import com.mycompany.cafe_management_app.dao.BillDetailDao;
import com.mycompany.cafe_management_app.dao.DishDao;
import com.mycompany.cafe_management_app.dao.DishDetailDao;
import com.mycompany.cafe_management_app.dao.RevenueDao;
import com.mycompany.cafe_management_app.dao.SalaryDao;
import com.mycompany.cafe_management_app.dao.StaffDao;
import com.mycompany.cafe_management_app.dao.TimekeepingDao;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.BillDetail;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;
import com.mycompany.cafe_management_app.model.Revenue;
import com.mycompany.cafe_management_app.model.Salary;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.dao.*;
import com.mycompany.cafe_management_app.model.*;
import com.mycompany.cafe_management_app.util.ClientUtil;
import com.mycompany.cafe_management_app.util.JSONObjUtil;
import com.mycompany.cafe_management_app.util.UserSession;
import com.mysql.cj.xdevapi.SessionFactory;

import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author Hieu
 */
public class StaffService {
    private final TimekeepingDao timekeepingDao;
    private final DishDao dishDao;
    private final DishDetailDao dishDetailDao;
    private final BillDao billDao;
    private final BillDetailDao billDetailDao;
    private final StaffDao staffDao;
    private final RevenueDao revenueDao;
    private final SalaryDao salaryDao;
    private final Staff currentStaff;

    public StaffService() {
        timekeepingDao = new TimekeepingDao();
        dishDao = new DishDao();
        dishDetailDao = new DishDetailDao();
        billDao = new BillDao();
        billDetailDao = new BillDetailDao();
        staffDao = new StaffDao();
        revenueDao = new RevenueDao();
        salaryDao = new SalaryDao();
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
        Double payment = 0.0;
        Double payment1 = 0.0;

        LocalDate current = LocalDate.now();
        int year = current.getYear();
        int month = current.getMonthValue();
        LocalDate currentMonthYear = LocalDate.of(year, month, 1);

        List<Timekeeping> list = new ArrayList<>();
        List<Salary> salaryList = new ArrayList<>();
        Salary salary = new Salary();

        // Set checkout time
        t.setCheckoutTime(currentTime);

        // Calculate total work time
        DecimalFormat df = new DecimalFormat("#.##");
        Duration duration = Duration.between(t.getCheckinTime(), currentTime);
        double hours = duration.toMillis() / (double) (1000 * 60 * 60);
        Double formattedHours = Double.valueOf(df.format(hours));
        t.setTotalTime(formattedHours);

        // Calculate payment
        t.setTotalPayment(currentStaff.getHourlyRate() * formattedHours);
        timekeepingDao.update(t);

        // Calculate the staff salary
        salary = salaryDao.getByID(currentStaffID, currentMonthYear);
        if (salary == null) {
            payment1 += t.getTotalPayment();
            Salary sal = new Salary();
            sal.setAmount(payment);
            sal.setStaff(currentStaff);
            sal.setTime(currentMonthYear);
            System.out.println("Payment : " + payment1);
            salaryDao.save(sal);

        } else {
            // Double temp = t.getTotalPayment();x
            payment += t.getTotalPayment();
            salary.setAmount(salary.getAmount() + payment);
            salaryDao.update(salary);
            System.out.println("Payment : " + payment);

        }

//        Upsert the revenue to DB
        upsertRevenueOutcome(t);

        // Send CMD=END to server
        // ClientUtil.getInstance().sendRequestAsync(JSONObjUtil.toJson(null, "END"));
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

    public CompletableFuture<String> createBillAsync(
            Bill bill,
            Double receivedAmount,
            String cardNumber) {

//        no card -> pay by cash
        if (cardNumber.isEmpty()) {
            if (receivedAmount < bill.getTotalPrice()) {
                return new CompletableFuture<String>().completeAsync(() -> "CASH_FAILED");
            }

            return new CompletableFuture<String>().completeAsync(() -> {
                bill.setPaymentMethod("cash");
                bill.setReceivedAmount(receivedAmount);
                bill.setReturnedAmount(receivedAmount - bill.getTotalPrice());
                billDao.save(bill);

//                Upsert the revenue
                upsertRevenueIncome(bill);

                return "SUCCESS";
            });
        }

//        pay by card
//        send request to server
        System.out.println("Card number: " + cardNumber);
        bill.setCardNumber(cardNumber);
        return ClientUtil.getInstance().sendRequestAsync(JSONObjUtil.toJson(bill, "TRANSACTION"))
                .thenApply(res -> {
                    System.out.println("Response from server: " + res);

                    if (JSONObjUtil.getHeader(res).equals("RESPONSE") &&
                            JSONObjUtil.getBody(res).equals("SUCCESS")) {
                        bill.setPaymentMethod("card");
                        bill.setReceivedAmount(bill.getTotalPrice());
                        bill.setReturnedAmount(0.0);

                        billDao.save(bill);
                        System.out.println("Bill saved");

//                        Upsert the revenue
                        upsertRevenueIncome(bill);

                        return "SUCCESS";
                    }

                    return "TRANSACTION_FAILED";
                });
        }

    public List<Bill> getAllBill() {
        return billDao.getAll();
    }
    
    public List<BillDetail> getDetailsByBillID(Long id) {
        return billDetailDao.getByBillID(id);
    }

    public CompletableFuture<String> makeTransactionAsync(Bill bill, String cardNumber) {
        return ClientUtil.getInstance().sendRequestAsync(JSONObjUtil.toJson(bill, "TRANSACTION"));
    }

    public String getTodayRevenue() {
        Double total = revenueDao.getByDate(LocalDate.now()).getTotal();
        if (total == null) {
            return "0";
        }
        return total.toString();
    }

    private void upsertRevenueIncome(Bill bill) {
        Revenue revenue = revenueDao.getByDate(bill.getTimeCreated().toLocalDate());
        if (revenue == null) {
            revenue = new Revenue(bill.getTimeCreated().toLocalDate());
            revenue.setIncome(bill.getTotalPrice());
            revenueDao.save(revenue);
        } else {
            revenue.setIncome(revenue.getIncome() + bill.getTotalPrice());
            revenueDao.update(revenue);
        }
    }


    public String getTotalRevenue() {
        Double totalRevenue = 0.0;
        List<Bill> bills = billDao.getAll();
        LocalDateTime currentTime = LocalDateTime.now();

        for (Bill b : bills) {
            if (b.getTimeCreated().getDayOfMonth() == currentTime.getDayOfMonth()
                    && b.getTimeCreated().getMonthValue() == currentTime.getMonthValue()
                    && b.getTimeCreated().getYear() == currentTime.getYear()) {

                        totalRevenue += b.getTotalPrice();
                    }
        }
        return String.format("%,.0f", totalRevenue);

    private void upsertRevenueOutcome(Timekeeping t) {
        Revenue revenue = revenueDao.getByDate(t.getCheckinTime().toLocalDate());
        if (revenue == null) {
            revenue = new Revenue(t.getCheckinTime().toLocalDate());
            revenue.setOutcome(t.getTotalPayment());
            revenueDao.save(revenue);
        } else {
            revenue.setOutcome(revenue.getOutcome() + t.getTotalPayment());
            revenueDao.update(revenue);
        }
    }

}

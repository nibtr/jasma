/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 *
 * @author Hieu
 */

@Entity(name = "Timekeeping")
public class Timekeeping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timekeeping_id")
    private Long id;
    
    @Column(name = "checkin_time", columnDefinition = "DATETIME")
    private LocalDateTime checkinTime;

    @Column(name = "checkout_time", columnDefinition = "DATETIME")
    private LocalDateTime checkoutTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_staff_id")
    private Staff staff;
    
    @Column(name = "total_time_in_hour")
    private Double totalTimeInHour;
    
    @Column(name = "payment")
    private Double payment;

    public Timekeeping() {
        this.checkinTime = LocalDateTime.now();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(LocalDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }

    public LocalDateTime getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(LocalDateTime checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Double getTotalTime() {
        return totalTimeInHour;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTimeInHour = totalTime;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Double getTotalPayment() {
        return payment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.payment = totalPayment;
    }

}

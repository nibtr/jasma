/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hieu
 */
@Entity(name = "Bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long id;
    
    @Column(name = "time_created", columnDefinition = "DATETIME")
    private LocalDateTime timeCreated;
    
    @Column(name = "total_price")
    private Double totalPrice;
    
    @Column(name = "received_amount")
    private Double receivedAmount;
    
    @Column(name = "returned_amount")
    private Double returnedAmount;
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillDetail> billDetails = new ArrayList<>();
    
  
    public Bill() {
//        required by Hibernate
    }

    public Bill(LocalDateTime timeCreated) {
        this.totalPrice = 0.0;
        this.timeCreated = timeCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Double getReturnedAmount() {
        return returnedAmount;
    }

    public void setReturnedAmount(Double returnedAmount) {
        this.returnedAmount = returnedAmount;
    }
    
    public List<BillDetail> getBillDetails() {
        return billDetails;
    }

//    public void setBillDetails(List<BillDetail> billDetails) {
//        this.billDetails = billDetails;
//    }

    public void addBillDetail(BillDetail b) {
        billDetails.add(b);
        b.setBill(this);
        this.totalPrice += b.getPrice();
    }

}

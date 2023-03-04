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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Hieu
 */
@Entity(name = "BillDetail")
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_id")
    private Long id;
    
    @ManyToOne   
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "fk_bill_id")
    private Bill bill;
    
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "fk_dish_detail_id")
    private DishDetail dishDetail;
    
    @Column(name = "quantity", nullable = false)
    private Long quantity;
    
    @Column(name = "price")
    private Double price;
    
    public BillDetail(DishDetail d, Long quantity) {
        this.dishDetail = d;
        this.quantity = quantity;
        this.price = d.getPrice() * quantity;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public DishDetail getDishDetail() {
        return dishDetail;
    }

    public void setDishDetail(DishDetail dishDetail) {
        this.dishDetail = dishDetail;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
}

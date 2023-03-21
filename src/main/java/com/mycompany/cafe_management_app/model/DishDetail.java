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
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hieu
 */
@Entity(name = "DishDetail")
public class DishDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dish_detail_id")
    private Long id;
 
    @Column(name = "size")
    private String size;
    
    @Column(name = "price")
    private Double price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_dish_id")
    private Dish dish;
    
    @OneToMany(mappedBy = "dishDetail", orphanRemoval = true)
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<BillDetail> billDetails = new ArrayList<>();

    public DishDetail() {
        
    }

    public DishDetail(String size, Double price) {
        this.size = size;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

//    public List<BillDetail> getBillDetails() {
//        return billDetails;
//    }
//
//    public void setBillDetails(List<BillDetail> billDetails) {
//        this.billDetails = billDetails;
//    }
//    
//    public void addBillDetail(BillDetail b) {
//        billDetails.add(b);
//        b.setDishDetail(this);
//    }
}

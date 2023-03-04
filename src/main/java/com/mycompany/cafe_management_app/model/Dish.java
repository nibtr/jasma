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
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Legion
 */
@Entity(name = "Dish")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dish_id")
    private Long id;

    @Column(name="name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishDetail> details = new ArrayList<>();

    public Dish() {
//        required by Hibernate
    }

    public Dish(String name) {
        this.name = name;    
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DishDetail> getDetails() {
        return details;
    }

    public void setDetails(List<DishDetail> details) {
        this.details = details;
        
    }
    
    public void addDetail(DishDetail detail) {
        details.add(detail);
        detail.setDish(this);
    }

}

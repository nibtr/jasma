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
import java.time.LocalDate;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.model.Timekeeping;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;

/**
 *
 * @author Legion
 */
@Entity(name = "Salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revenue_id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "time", columnDefinition = "DATETIME")
    private LocalDate time;
            
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_staff_id")
    private Staff staff;

    public Salary() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate Time) {
        this.time = Time;
    }
    
    public Staff getStaff() {
        return staff;
    }

    public void setSalary(Staff staff) {
        this.staff = staff;
    }
    
}

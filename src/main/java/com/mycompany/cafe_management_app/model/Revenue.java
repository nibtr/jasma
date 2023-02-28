/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 *
 * @author Legion
 */
@Entity(name = "Revenue")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "revenue_id")
    private Long id;

    @Column(name = "total_cash_in_month")
    private Double total;

    @Column(name = "income_cash_in_month")
    private Double income;

    @Column(name = "outcome_cash_in_month")
    private Double outcome;

    @Column(name = "present_time", columnDefinition = "DATETIME")
    private LocalDateTime time;
    
    

    public Revenue() {
    }

    public Revenue(Double income, Double outcome) {

        this.income = income;
        this.outcome = outcome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double income, Double outcome) {
        this.total = income - outcome;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getOutcome() {
        return outcome;
    }

    public void setOutcome(Double outcome) {
        this.outcome = outcome;
    }
    
    public LocalDateTime getCheckinTime() {
        return time;
    }

    public void setCheckinTime(LocalDateTime Time) {
        this.time = Time;
    }
}

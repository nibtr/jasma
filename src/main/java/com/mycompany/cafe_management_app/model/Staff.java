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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Hieu
 */
@Entity(name = "Staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @Column
    private String position;
    
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @Column(name = "hourly_rate")
    private Double hourlyRate;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_id")
    private Account account;
    
    @OneToMany(mappedBy = "staff" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salary> salaries = new ArrayList<>();
    
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timekeeping> listTimekeeping = new ArrayList<>();

   
    public Staff(){
//        required by Hibernate
        this.hourlyRate = 2.5;
    }
    
    public Staff(String name, LocalDate dob, String phoneNumber, String position) {
        this();
        this.name = name;
        this.dateOfBirth = dob;
        this.phoneNumber = phoneNumber;
        this.position = position;
    }
    
    public Staff(String name, LocalDate dob, String phoneNumber) {
        this();
        this.name = name;
        this.dateOfBirth = dob;
        this.phoneNumber = phoneNumber;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    
    public void setAccount(Account t) {
        this.account = t;
    }
    
    public Account getAccount() {
        return account;
    }

    public List<Timekeeping> getListTimekeeping() {
        return listTimekeeping;
    }

//    public void setListTimekeeping(List<Timekeeping> listTimekeeping) {
//        this.listTimekeeping = listTimekeeping;
//    }
    
    public void addTimekeeping(Timekeeping t) {
        this.listTimekeeping.add(t);
        t.setStaff(this);
    }

    public void setAll(String name, LocalDate dob, String phoneNumber, String position) {
        this.name = name;
        this.dateOfBirth = dob;
        this.phoneNumber = phoneNumber;
        this.position = position;
    }

    public void addSalary(Salary salary){
        salaries.add(salary);
        salary.setSalary(this);
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }
    
}

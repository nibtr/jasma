/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import java.util.List;



/**
 *
 * @author Hieu
 */
public interface DaoInterface<T> {
    List<T> getAll();
//    T getById(Long id);
    void save(T t);
    void update(T t);
    void delele(T t);
}

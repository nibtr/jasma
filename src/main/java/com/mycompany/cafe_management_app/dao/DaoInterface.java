/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.cafe_management_app.dao;

import com.mycompany.cafe_management_app.model.Dish;
import java.util.List;



/**
 *
 * @author Hieu
 */
public interface DaoInterface<T> {
    List<T> getAll();
    void save(T t);
    void update(T t);
    void delete(T t);
}

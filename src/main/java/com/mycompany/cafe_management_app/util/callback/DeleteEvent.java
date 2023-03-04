/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.util.callback;

import com.mycompany.cafe_management_app.controller.DashboardAdminController.DeleteFunc;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;

/**
 *
 * @author namho
 */
public class DeleteEvent {
    private DeleteFunc func;
    public DeleteEvent(DeleteFunc func) {
        this.func = func;
    }
    
    public void delete(Staff staff) {
        func.execute(staff);
    }
}

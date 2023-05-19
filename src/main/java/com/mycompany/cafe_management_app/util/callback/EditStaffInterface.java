/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.util.callback;

import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.NewStaffForm;

/**
 *
 * @author namho
 */
public interface EditStaffInterface {
    public void execute(NewStaffForm form, Staff editedStaff);
}

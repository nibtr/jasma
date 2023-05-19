/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.controller.DashboardAdminController.DeleteFunc;
import com.mycompany.cafe_management_app.controller.DashboardAdminController.EditFunction;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.ConfirmBeforeDelete;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.NewStaffForm;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.SearchStaffUI;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.StaffItem;
import com.mycompany.cafe_management_app.util.ErrorUtil;
import com.mycompany.cafe_management_app.util.StaffUtil;
import com.mycompany.cafe_management_app.util.callback.DeleteEvent;
import com.mycompany.cafe_management_app.util.callback.DeleteStaffInterface;
import com.mycompany.cafe_management_app.util.callback.EditEvent;
import com.mycompany.cafe_management_app.util.callback.EditStaffInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author namho
 */
public class SearchStaffController {
    SearchStaffUI searchStaffUI = null;
    private JPanel staffContainer;
    private JTextField staffInput;
    private AdminService adminService;
    private List<Staff> listStaff;
    private ErrorUtil errorUtil;
    private StaffUtil staffUtil;
    private Supplier<Void> rerenderParentUI;
    
    public SearchStaffController(AdminService adminService, ErrorUtil errorUtil, Supplier<Void> rerenderParentUI) {
        this.adminService = adminService;
        this.errorUtil = errorUtil;
        this.rerenderParentUI = rerenderParentUI;
        init();
    }
    
    public class EditFunction implements EditStaffInterface{
        @Override
        public void execute(NewStaffForm form, Staff editedStaff) {
            staffUtil.updateStaff(form, editedStaff, adminService, errorUtil);
            renderListStaff(adminService.getAllStaff());
        }
    }
    
        public class DeleteFunc implements DeleteStaffInterface {
        @Override
        public void execute(Staff staff) {
            ConfirmBeforeDelete res = new ConfirmBeforeDelete(new Supplier<Void>() {
                @Override
                public Void get() {
                    adminService.deleteStaff(staff);
                    if (errorUtil.getErrorCode() == 0) {
                        new NotificationController("Delete staff successfully !");
                    } else {
                        new NotificationController("Delete staff failed !");
                    }
                    renderListStaff(adminService.getAllStaff());
                    return null;
                }
            });
            res.setVisible(true);
        }
    }
        
        private void renderListStaff(List<Staff> listStaff) {
            staffContainer.removeAll();
            for (Staff item: listStaff) {
                String name = item.getName();
                String dob = item.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM yyyy"));
                String phone = item.getPhoneNumber();
                String pos = item.getPosition();
                EditEvent editEvent = new EditEvent(new EditFunction());
                DeleteEvent deleteEvent = new DeleteEvent(new DeleteFunc());
                StaffItem tmp = new StaffItem(item, name, dob, phone, pos, editEvent, deleteEvent);
                staffContainer.add(tmp);
            }
            searchStaffUI.dispose();
            searchStaffUI.setVisible(true);
        }
    
    private void init() {
        searchStaffUI = new SearchStaffUI();
        searchStaffUI.addWindowListener(new CloseWindowAction());
        staffUtil = new StaffUtil();
        staffContainer = searchStaffUI.getSearchContainer();
        staffInput = searchStaffUI.getInputField();
        searchStaffUI.getSearchBtn().addActionListener(new searchBtnAction());
        renderListStaff(adminService.getAllStaff());

    }
    
    private class CloseWindowAction implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            rerenderParentUI.get();
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
        
    }
    
    private class searchBtnAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchResult = staffInput.getText();
            if (!searchResult.equals("")) {
                listStaff = adminService.searchStaffByName(searchResult);
                if (errorUtil.getErrorCode() == 0) {
                    renderListStaff(listStaff);
                }
            } else {
                renderListStaff(adminService.getAllStaff());
            }
        }
    }
}

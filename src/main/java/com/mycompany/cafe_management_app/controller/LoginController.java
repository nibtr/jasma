package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.service.LoginService;
import com.mycompany.cafe_management_app.ui.LoginUI;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.DashboardAdminUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DashboardStaffUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {

    private LoginUI loginUI;
    private LoginService loginService;
    private int role;

    public LoginController() {
        this.loginUI = new LoginUI();
        this.loginService = new LoginService();
        this.loginUI.getsigninButton().addActionListener(new SignInButtonListener());
    }

    private class SignInButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginUI.getuserNameField();
            String password = loginUI.getpasswordField();

            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(loginUI, "Please enter username and password!");
                return;
            }

            // Check username and password
            role = loginService.authenticate(username, password);

            if (role == 0) {
                JOptionPane.showMessageDialog(loginUI, "ADMIN LOGGED IN!");
                DashboardAdminUI adminUI = new DashboardAdminUI();
                adminUI.setVisible(true);
                loginUI.dispose();
            } else if (role == 1) {
                JOptionPane.showMessageDialog(loginUI, "STAFF LOGGED IN");
                DashboardStaffUI staffUI = new DashboardStaffUI();
                staffUI.setVisible(true);
                loginUI.dispose();
            } else {
                JOptionPane.showMessageDialog(loginUI, "Account not found!");
            }
        }

        public int getRole() {
            return role;
        }
    }
    
    public void setLoginUI(LoginUI loginUI) {
        this.loginUI = loginUI;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }
    public LoginUI getLoginUI() {
        return loginUI;
    }
}
package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.service.LoginService;
import com.mycompany.cafe_management_app.ui.InitErrorUI;
import com.mycompany.cafe_management_app.ui.LoginUI;
import com.mycompany.cafe_management_app.util.ClosePaymentConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.*;

public class LoginController {

    private LoginUI loginUI;
    private LoginService loginService;
    private int role;

    public LoginController() {
        this.loginUI = new LoginUI();
        this.loginUI.addWindowListener(new CloseConnection());
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
                JOptionPane.showMessageDialog(loginUI, "LOGGED IN SUCCESSFULLY!");
                new DashboardAdminController();
                loginUI.dispose();
            } else if (role == 1) {
                JOptionPane.showMessageDialog(loginUI, "LOGGED IN SUCCESSFULLY!");
                new DashboardStaffController();
                loginUI.dispose();
            } else {
                JOptionPane.showMessageDialog(loginUI, "CREDENTIALS DON'T MATCH!");
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

    private class CloseConnection implements WindowListener {
        @Override
        public void windowClosing(WindowEvent e) {
            loginUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            int confirmed = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                try {
                    ClosePaymentConnection.closeConnectionWithPaymentServer();
                    loginUI.dispose();
                    System.exit(0);
                } catch (IOException ex) {
                    new InitErrorUI("Error", "Cannot close connection with payment server");
                    ex.printStackTrace();
                }
            }
        }
        @Override
        public void windowOpened(WindowEvent e) {
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
}
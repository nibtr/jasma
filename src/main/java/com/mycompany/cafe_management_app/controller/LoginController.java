package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.ui.LoginUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {

    private LoginUI loginUI;

    public LoginController(LoginUI loginUI) {
        this.loginUI = loginUI;

        loginUI.getSigninButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUI.getUsernameField();
                String password = loginUI.getPasswordField();

                // Check username and password
                if (username.equals("admin") && password.equals("admin")) {
                    JOptionPane.showMessageDialog(loginUI, "Successful!");
                } else {
                    JOptionPane.showMessageDialog(loginUI, "Failed!");
                }
            }
        });
    }

    public LoginUI getLoginUI() {
        return loginUI;
    }
}
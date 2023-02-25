package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.ui.LoginUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {

    private LoginUI loginUI;

    public LoginController(LoginUI loginUI) {
        this.loginUI = loginUI;
        this.loginUI.getSigninButton().addActionListener(new SignInButtonListener());
    }

    private static class SignInButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            String username = loginUI.getUsernameField();
//            String password = loginUI.getPasswordField();

            // Check username and password
            // if (loginService.authenticate(username, password))) {
            //     JOptionPane.showMessageDialog(loginUI, "Successful!");
            // } else {
            //     JOptionPane.showMessageDialog(loginUI, "Failed!");
            // }
        }
    }
    
    

}

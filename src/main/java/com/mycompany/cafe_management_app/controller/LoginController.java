package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.service.LoginService;
import com.mycompany.cafe_management_app.ui.LoginUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {

    private LoginUI loginUI;
    private LoginService loginService;

    public LoginController(LoginUI loginUI, LoginService loginService) {
        this.loginUI = loginUI;
        this.loginService = loginService;
        this.loginUI.getsigninButton().addActionListener(new SignInButtonListener());
    }

    private class SignInButtonListener implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginUI.getuserNameField();
            String password = loginUI.getpasswordField();

//             Check username and password
            Integer res = loginService.authenticate(username, password);
            if ( res.equals(0)) {
                JOptionPane.showMessageDialog(loginUI, "ADMIN LOGGED IN!");
            } else if (res.equals(1)){
                JOptionPane.showMessageDialog(loginUI, "STAFF LOGGED IN");
            } else {
                JOptionPane.showMessageDialog(loginUI, "Failed!");
            }
        }
    }

    public LoginUI getLoginUI() {
        return loginUI;
    }
}


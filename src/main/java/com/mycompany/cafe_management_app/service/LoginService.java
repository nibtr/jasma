/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.service;

import com.mycompany.cafe_management_app.dao.AccountDao;
import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.util.PasswordUtil;
import com.mycompany.cafe_management_app.util.UserSession;

/**
 *
 * @author Hieu
 */
public class LoginService {
    private AccountDao accountDao;
    
    public LoginService() {
        accountDao = new AccountDao();
    }
    
    public Integer authenticate(String username, String inputPassword) {
//        -1: no exist or wrong credentials
//         0: admin
//         1: staff

//        If username doesn't exist
        Account account = accountDao.getByUsername(username);
        if (account == null) {
            return -1;
        }
        
//        Username exists but password doesn't match
        boolean res = PasswordUtil.comparePassword(inputPassword, account.getPassword());
        if (!res) {
            return -1;
        }
        
//        Credentials match
//        UserSession.getInstance().setUserID(account.getId());
        if (account.getRole().equals("admin")) {
            return 0;
        }
       
        return 1;
    }
}

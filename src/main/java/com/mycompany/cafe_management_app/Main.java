/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.dao.AccountDao;
import com.mycompany.cafe_management_app.model.Account;
import com.mycompany.cafe_management_app.service.LoginService;

/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        Account account = new Account("admin", "admin", "admin");
        AccountDao accountDao = new AccountDao();
//        accountDao.save(account);
//        accountDao.delele(accountDao.getAccountByUsername("admin"));

        LoginService loginService = new LoginService();
        System.out.println(loginService.authenticate("admin", "admin"));
    }
}

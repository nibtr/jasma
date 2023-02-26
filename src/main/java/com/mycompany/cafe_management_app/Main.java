/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cafe_management_app;

import com.mycompany.cafe_management_app.dao.AccountDao;
import com.mycompany.cafe_management_app.model.Account;

/**
 *
 * @author Hieu
 */
public class Main {

    public static void main(String[] args) {
        Account account = new Account("staff1", "staff1", "staff");
        AccountDao accountDao = new AccountDao();
        accountDao.save(account);
//        accountDao.delele(accountDao.getAccountByUsername("admin"));
    }
}

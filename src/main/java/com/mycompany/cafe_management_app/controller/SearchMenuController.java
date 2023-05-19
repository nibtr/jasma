/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.controller.DashboardAdminController.DeleteFunc;
import com.mycompany.cafe_management_app.controller.DashboardAdminController.EditFunction;
import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;
import com.mycompany.cafe_management_app.model.Staff;
import com.mycompany.cafe_management_app.service.AdminService;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.ConfirmBeforeDelete;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.DetailsItem;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.DishForm;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.NewStaffForm;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.SearchMenuUI;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.SearchStaffUI;
import com.mycompany.cafe_management_app.ui.DashboardAdminUI.StaffItem;
import com.mycompany.cafe_management_app.ui.DetailsDish;
import com.mycompany.cafe_management_app.ui.MenuItem;
import com.mycompany.cafe_management_app.util.ErrorUtil;
import com.mycompany.cafe_management_app.util.StaffUtil;
import com.mycompany.cafe_management_app.util.callback.DeleteDishInterface;
import com.mycompany.cafe_management_app.util.callback.DeleteEvent;
import com.mycompany.cafe_management_app.util.callback.DeleteStaffInterface;
import com.mycompany.cafe_management_app.util.callback.DetailDishInterface;
import com.mycompany.cafe_management_app.util.callback.EditDishInterface;
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
public class SearchMenuController {
    SearchMenuUI searchMenuUI;
    private JPanel menuContainer;
    private JTextField menuInput;
    private AdminService adminService;
    private ErrorUtil errorUtil;
    private List<Dish> listDish;
    private Supplier<Void> rerenderParentUI;
    
    public SearchMenuController(AdminService adminService, ErrorUtil errorUtil, Supplier<Void> rerenderParentUI) {
        this.adminService = adminService;
        this.errorUtil = errorUtil;
        this.rerenderParentUI = rerenderParentUI;
        init();
    }
   
       
    
    private void init() {
        searchMenuUI = new SearchMenuUI();
        searchMenuUI.addWindowListener(new CloseWindowAction());
        menuContainer = searchMenuUI.getSearchContainer();
        menuInput = searchMenuUI.getInputField();
        searchMenuUI.getSearchBtn().addActionListener(new searchBtnAction());

    }
    
    public class DetailsDishFunction implements DetailDishInterface {
        @Override
        public void showDetails(Long id) {
            DetailsDish frame = new DetailsDish();
            JPanel container = frame.getContainer();
            List<DishDetail> list = adminService.getDetailsOf(id);
            if (errorUtil.getErrorCode() == 0) {
                for (int i = 0; i < list.size(); i++) {
                     String size = list.get(i).getSize();
                     String price = list.get(i).getPrice().toString();
                     container.add(new DetailsItem(size, price));
                 }
                 frame.setVisible(true);
            }
        }
   }
   
   public class DeleteDishFunction implements DeleteDishInterface {
       @Override
       public void delete(Dish dish) {
           ConfirmBeforeDelete res = new ConfirmBeforeDelete(new Supplier<Void>() {
               @Override
               public Void get() {
                    adminService.deleteDish(dish);
                    if (errorUtil.getErrorCode() == 0) {
                        new NotificationController("Delete dish successfully !");
                    } else {
                        new NotificationController("Delete dish failed !");
                    }
                    searchMenuUI.dispose();
                    searchMenuUI.setVisible(true);
                    return null;
                }
           });
           res.setVisible(true);
       }
   }
   
    public class EditDishFunction implements EditDishInterface {
       public void execute(Dish dish, Long id) {
           DishForm dishForm = new DishForm(dish.getName(), adminService.getDetailsOf(id));
           dishForm.getSaveBtn().addActionListener(new SaveDishForm("update", dish));
           dishForm.setVisible(true);
       }
   }
    
    private void renderListMenu(List<Dish> listDish) {
        for (Dish dish : listDish) {
            menuContainer.add(new MenuItem(dish, new DetailsDishFunction(), new EditDishFunction(), new DeleteDishFunction(), Boolean.FALSE));
        }
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
            String searchResult = menuInput.getText();
            if (!searchResult.equals("")) {
                listDish = adminService.searchDishByName(searchResult);
                if (errorUtil.getErrorCode() == 0) {
                    renderListMenu(listDish);
                }
            } else {
                renderListMenu(adminService.getAllDish());
            }
        }
    }
}

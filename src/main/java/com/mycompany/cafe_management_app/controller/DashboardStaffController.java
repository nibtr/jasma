package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DashboardStaffUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DetailsItemStaff;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.MenuItemStaff;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.OrderHistory;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.NewOrderForm;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.NewDishForm;
import com.mycompany.cafe_management_app.ui.MenuItem;
import com.mycompany.cafe_management_app.ui.DetailsDish;

import com.mycompany.cafe_management_app.model.Timekeeping;
import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.BillDetail;

import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;

import com.mycompany.cafe_management_app.service.StaffService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import java.awt.Component;

import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author namho
 */
public class DashboardStaffController {
    private DashboardStaffUI dashboardStaffUI;
    private StaffService staffService;
    private JPanel wrapListBill;
    private JPanel wrapListDish;
    private JPanel wrapChooseDish;
    private NewOrderForm NewOrderForm;
    private NewDishForm NewDishForm;

    public DashboardStaffController() {
        initController();
    }

    public void checkIn() {
        LocalDateTime currentTime = LocalDateTime.now();
        staffService.checkIn(currentTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = currentTime.format(formatter);
        JOptionPane.showMessageDialog(dashboardStaffUI, "     CHECK IN SUCCESSFUL!\n" + "        " + formatDateTime);
    }

    public void checkOut() {
        JFrame jframe = new JFrame("EXIT");
        if (JOptionPane.showConfirmDialog(jframe, "Confirm if you want to exit", "Time Keeping",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
        }

        LocalDateTime currentTime = LocalDateTime.now();
        staffService.checkOut(currentTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = currentTime.format(formatter);
        JOptionPane.showMessageDialog(dashboardStaffUI, "     CHECK OUT SUCCESSFUL!\n" + "        " + formatDateTime);
        System.exit(0);
    }

    private class CheckInOutButtonListener implements ActionListener {
        private boolean checkedIn = true;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkedIn) {
                checkIn();
                checkedIn = false;

            } else {
                checkOut();
            }
        }
    }

    private void renderListOrder() {
        List<Bill> listBill = staffService.getAllBill();
        for (int i = 0; i < listBill.size(); i++) {
            Bill tmpOrder = listBill.get(i);
            LocalDateTime time = tmpOrder.getTimeCreated();
            Double totalPriceLabel = tmpOrder.getTotalPrice();
            Double receiveAmountLabel = tmpOrder.getReceivedAmount();
            Double returnAmountLabel = tmpOrder.getReturnedAmount();
            OrderHistory tmp = new OrderHistory(tmpOrder, time, totalPriceLabel, receiveAmountLabel, returnAmountLabel);
            wrapListBill.add(tmp);
            wrapListBill.repaint();
            wrapListBill.revalidate();
        }
    }

    private void renderListMenu() {
        List<Dish> listDish = staffService.getAllDish();
        for (Dish dish : listDish) {
            MenuItemStaff menuItem = new MenuItemStaff(dish, new DetailsDishFunction());
            wrapListDish.add(menuItem);
            wrapListDish.repaint();
            wrapListDish.revalidate();
        }
    }

    public class DetailsDishFunction {
        private DetailsDishFunction() {
        }

        public void showDetails(Dish dish) {
            DetailsDish frame = new DetailsDish();
            JPanel container = frame.getContainer();
            List<DishDetail> list = staffService.getDetailsOf(dish);
            for (int i = 0; i < list.size(); i++) {
                String size = list.get(i).getSize();
                String price = list.get(i).getPrice().toString();

                container.add(new DetailsItemStaff(dish, size, price, DetailsDishFunction()));
            }

            frame.setVisible(true);

            NewOrderForm.getAddOrderButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                }
            });
        }

        private DetailsDishFunction DetailsDishFunction() {
            return new DetailsDishFunction();
        }

        public void addDishes(Dish dish, String size, Double price) {

            NewDishForm newDish = new NewDishForm(dish, size, price);
            wrapChooseDish.add(newDish);
            wrapChooseDish.repaint();
            wrapChooseDish.revalidate();

            // Total Price
            Double totalPrice = 0.0;
            for (int i = 0; i < wrapChooseDish.getComponentCount(); i++) {
                totalPrice += price;
            }
            NewOrderForm.getTotalPriceLabel().setText(totalPrice.toString());

            // Delete dish
            newDish.getDeleteDishButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    wrapChooseDish.remove(newDish);
                    wrapChooseDish.repaint();
                    wrapChooseDish.revalidate();
                }
            });
        }
    }

    private void initController() {
        staffService = new StaffService();
        NewOrderForm = new NewOrderForm();

        // check in/out button
        dashboardStaffUI = new DashboardStaffUI();
        dashboardStaffUI.getCheckInOutButton().addActionListener(new CheckInOutButtonListener());

        // show list bill
        wrapListBill = dashboardStaffUI.getContainerListBill();
        renderListOrder();

        // show new order form
        dashboardStaffUI.getAddOrderBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewOrderForm.setVisible(true);
            }
        });

        // Menu Dish
        wrapListDish = NewOrderForm.getContainerDishStaff();

        // Choose Dish
        wrapChooseDish = NewOrderForm.getContainerPayment();
        renderListMenu();

        // Cancel Order
        NewOrderForm.getCancelOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewOrderForm.setVisible(false);
                wrapChooseDish.removeAll();
            }
        });

        // Confirm Order
        NewOrderForm.getAddOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create new bill
                Bill bill = new Bill();
                LocalDateTime currentTime = LocalDateTime.now();
                String tmpRA = NewOrderForm.getReceivedAmountField();
                Double receiveAmount = Double.parseDouble(tmpRA);
                bill.setTimeCreated(currentTime);

                // Add bill detail
                List<BillDetail> billDetails = new ArrayList<>();
                Double totalPrice = 0.0;
                for (int i = 0; i < wrapChooseDish.getComponentCount(); i++) {
                    Component component = wrapChooseDish.getComponent(i);
                    if (component instanceof NewDishForm) {
                        NewDishForm newDishForm = (NewDishForm) component;
                        Dish dish = newDishForm.getDish();
                        String size = newDishForm.getDishSizeLabel();
                        String tmp = newDishForm.getDishPriceLabel();
                        Double price = Double.parseDouble(tmp);

                        // DishDetail dishDetail = staffService.getDishDetail(dish, size, price);
                        // BillDetail billDetail = new BillDetail(dishDetail, 1L);
                        // billDetails.add(billDetail);

                        totalPrice += price;

                    }
                }

                System.out.println("Tổng tiền: " + totalPrice);

                // Set bill
                // bill.setBillDetails(billDetails);
                // bill.setTotalPrice(totalPrice);
                System.out.println("Giá: " + bill.getTotalPrice() + " Size:  " + bill.getBillDetails().size());

                // Add bill
                // staffService.createBill(bill, receiveAmount);

                // Show message
                JOptionPane.showMessageDialog(NewOrderForm, " ADD ORDER SUCCESSFUL!");

                // Close form
                NewOrderForm.setVisible(false);
                wrapChooseDish.removeAll();
            }
        });

        dashboardStaffUI.setVisible(true);
    }

}

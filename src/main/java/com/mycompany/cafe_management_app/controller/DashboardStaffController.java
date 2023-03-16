package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DashboardStaffUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DetailsItemStaff;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.MenuItemStaff;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.OrderHistory;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.NewOrderForm;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.NewDishForm;
import com.mycompany.cafe_management_app.ui.DetailsDish;

import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.BillDetail;

import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;

import com.mycompany.cafe_management_app.service.StaffService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.List;
import java.awt.Component;

import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.swing.JFrame;
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

    private Map<DishDetail, Integer> dishDetailQuantities = new HashMap<>();

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
            for (DishDetail dt : list) {
                // System.out.println(dt.getSize());
                // String size = list.get(i).getSize();
                // String price = list.get(i).getPrice().toString();
                container.add(new DetailsItemStaff(dt, DetailsDishFunction()));
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

        private Double calculateTotalPrice() {
            Double totalPrice = 0.0;
            for (Map.Entry<DishDetail, Integer> entry : dishDetailQuantities.entrySet()) {
                totalPrice += entry.getKey().getPrice() * entry.getValue();
            }
            return totalPrice;
        }

        public void addDishDetail(DishDetail dishDetail) {
            dishDetailQuantities.put(dishDetail, dishDetailQuantities.getOrDefault(dishDetail, 0) + 1);
            NewDishForm newDish = new NewDishForm(dishDetail);

            // Nếu đã có món ăn này trong order thì chỉ cần tăng số lượng lên 1
            for (Component component : wrapChooseDish.getComponents()) {
                if (component instanceof NewDishForm) {
                    NewDishForm tmp = (NewDishForm) component;
                    // Nếu đã có món ăn này trong order thì chỉ cần tăng số lượng lên 1
                    if (tmp.getDishDetailId().equals(dishDetail.getId())
                            && tmp.getDishSizeLabel().equals(dishDetail.getSize())) {
                        tmp.setQuantityDish(dishDetailQuantities.get(dishDetail).toString());
                        // Display total price
                        Double totalPrice = calculateTotalPrice();
                        NewOrderForm.getTotalPriceLabel().setText(totalPrice.toString());
                        return;

                    }
                }
            }
            // Print test
            for (Map.Entry<DishDetail, Integer> entry : dishDetailQuantities.entrySet()) {
                System.out.println(entry.getKey().getId() + " " + entry.getValue());
            }

            wrapChooseDish.add(newDish);
            wrapChooseDish.repaint();
            wrapChooseDish.revalidate();

            // Display total price
            Double totalPrice = calculateTotalPrice();
            NewOrderForm.getTotalPriceLabel().setText(totalPrice.toString());

            // Delete dish
            newDish.getDeleteDishButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Integer quantity = dishDetailQuantities.get(dishDetail);
                    String tempAmount = newDish.getQuantityDish();
                    int amount = Integer.parseInt(tempAmount);

                    if (quantity == 1) {
                        dishDetailQuantities.remove(dishDetail);
                        wrapChooseDish.remove(newDish);

                    } else {
                        dishDetailQuantities.put(dishDetail, quantity - 1);
                        String newAmountString = Integer.toString(amount - 1);
                        newDish.setQuantityDish(newAmountString);
                    }

                    wrapChooseDish.repaint();
                    wrapChooseDish.revalidate();

                    // Display total price
                    Double totalPrice = calculateTotalPrice();
                    NewOrderForm.getTotalPriceLabel().setText(totalPrice.toString());
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
                // Set total price to 0

                wrapChooseDish.removeAll();
                NewOrderForm.setVisible(false);
            }
        });

        // Confirm order (save order to database)
        NewOrderForm.getAddOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create new bill
                LocalDateTime currentTime = LocalDateTime.now();
                Bill bill = new Bill(currentTime);

                String tmpRA = NewOrderForm.getReceivedAmountField();
                Double receivedAmount = 0.0;
                try {
                    receivedAmount = Double.parseDouble(tmpRA);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Received amount must be a number");
                    return;
                }

                for (Map.Entry<DishDetail, Integer> entry : dishDetailQuantities.entrySet()) {
                    DishDetail dishDetail = entry.getKey();
                    Integer quantity = entry.getValue();
                    BillDetail billDetail = new BillDetail(dishDetail, quantity.longValue());
                    bill.addBillDetail(billDetail);
                }

                staffService.createBillAsync(bill, receivedAmount, "0123456789")
                        .thenApply(res -> {
                            System.out.println(res);
                            if (res.equals("SUCCESS")) {
                                JOptionPane.showMessageDialog(NewOrderForm, " ADD ORDER SUCCESSFUL!");

                                wrapListBill.removeAll();
                                renderListOrder();
                                wrapListBill.repaint();
                                wrapListBill.revalidate();

                                // Close form
                                NewOrderForm.setVisible(false);
                                wrapChooseDish.removeAll();
                            } else {
                                System.out.println("TRANSACTION FAILED!");
                                JOptionPane.showMessageDialog(NewOrderForm, " TRANSACTION FAILED!");
                            }

                            return res;
                        })
                        .thenAccept(res -> {
                            System.out.println("TRANSACTION PROCESS COMPLETED");
                        });

                // Show message

            }
        });

        dashboardStaffUI.setVisible(true);
    }

}

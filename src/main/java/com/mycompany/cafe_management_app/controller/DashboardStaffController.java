package com.mycompany.cafe_management_app.controller;

import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DashboardStaffUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.DetailsItemStaffUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.MenuItemStaffUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.OrderHistoryUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.NewOrderFormUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.NewDishFormUI;
import com.mycompany.cafe_management_app.ui.DashboardStaffUI.BillDetailsUI;
import com.mycompany.cafe_management_app.ui.DetailsDish;

import com.mycompany.cafe_management_app.model.Bill;
import com.mycompany.cafe_management_app.model.BillDetail;

import com.mycompany.cafe_management_app.model.Dish;
import com.mycompany.cafe_management_app.model.DishDetail;

import com.mycompany.cafe_management_app.service.StaffService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.lang.Integer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * @author namho
 */
public class DashboardStaffController {

    private DashboardStaffUI dashboardStaffUI;
    private StaffService staffService;
    private JPanel wrapListBill;
    private JPanel wrapListDish;
    private JPanel wrapChooseDish;
    private NewOrderFormUI NewOrderForm;

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
        LocalDateTime currentTime = LocalDateTime.now();
        for (int i = 0; i < listBill.size(); i++) {
            Bill tmpOrder = listBill.get(i);
            LocalDateTime time = tmpOrder.getTimeCreated();

            // Chỉ hiển thị những order trong ngày hôm nay
            if (time.getDayOfMonth() == currentTime.getDayOfMonth()
                    && time.getMonthValue() == currentTime.getMonthValue()
                    && time.getYear() == currentTime.getYear()) {
                OrderHistoryUI orderHistory = new OrderHistoryUI(tmpOrder);
                wrapListBill.add(orderHistory);
                wrapListBill.repaint();
                wrapListBill.revalidate();
            }

        }

    }


    private void renderListMenu() {
        List<Dish> listDish = staffService.getAllDish();
        for (Dish dish : listDish) {
            MenuItemStaffUI menuItem = new MenuItemStaffUI(dish, new DetailsDishFunction());
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
                container.add(new DetailsItemStaffUI(dt, DetailsDishFunction()));
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
            NewDishFormUI newDish = new NewDishFormUI(dishDetail);

            // Nếu đã có món ăn này trong order thì chỉ cần tăng số lượng lên 1
            for (Component component : wrapChooseDish.getComponents()) {
                if (component instanceof NewDishFormUI) {
                    NewDishFormUI tmp = (NewDishFormUI) component;
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
        NewOrderForm = new NewOrderFormUI();

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

        // Choose payment method
        NewOrderForm.getPayCashButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewOrderForm.setOptionPaymentLabel("Received:");

            }
        });

        NewOrderForm.getPayCardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewOrderForm.setOptionPaymentLabel("Card ID:");
            }
        });

        // Cancel Order
        NewOrderForm.getCancelOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set total price to 0
                Double totalPrice = 0.0;
                NewOrderForm.getTotalPriceLabel().setText(totalPrice.toString());

                // Clear dish detail quantities
                dishDetailQuantities.clear();

                // Clear wrapChooseDish
                NewOrderForm.setReceivedAmountField("");
                wrapChooseDish.removeAll();
                NewOrderForm.setVisible(false);
            }
        });

        // Confirm order (save order to database)
        NewOrderForm.getAddOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LocalDateTime now = LocalDateTime.now();
                Bill bill = new Bill(now);

                if (dishDetailQuantities.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please choose dish");
                    return;
                }

                // Check payment method
                boolean isPaymentByCard = NewOrderForm.getOptionPaymentLabel().getText().equals("Card ID:");
                String paymentValue = isPaymentByCard ? NewOrderForm.getReceivedAmountField() : "";
                Double receivedAmount = isPaymentByCard ? null
                        : Double.parseDouble(NewOrderForm.getReceivedAmountField());

                for (Map.Entry<DishDetail, Integer> entry : dishDetailQuantities.entrySet()) {
                    DishDetail dishDetail = entry.getKey();
                    Integer quantity = entry.getValue();
                    BillDetail billDetail = new BillDetail(dishDetail, quantity.longValue());
                    bill.addBillDetail(billDetail);
                }

                NewOrderForm.setStateProcessing("Processing . . .");
                staffService.createBillAsync(bill, receivedAmount, paymentValue)
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
                            NewOrderForm.setStateProcessing("");
                            Double totalPrice = 0.0;
                            NewOrderForm.getTotalPriceLabel().setText(totalPrice.toString());

                            // Clear dish detail quantities
                            dishDetailQuantities.clear();

                            // Clear wrapChooseDish
                            NewOrderForm.setReceivedAmountField("");
                            wrapChooseDish.removeAll();

                            // Calculate revenue
                            dashboardStaffUI.setRevenuLabel(staffService.getTotalRevenue().toString());
                            dashboardStaffUI.repaint();
                            dashboardStaffUI.revalidate();
                            System.out.println("TRANSACTION PROCESS COMPLETED");
                        });
            }

        });

        // Display revenue
        dashboardStaffUI.setRevenuLabel(staffService.getTotalRevenue().toString());
        
        dashboardStaffUI.setVisible(true);
    }

}

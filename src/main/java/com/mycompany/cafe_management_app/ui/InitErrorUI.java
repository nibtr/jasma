package com.mycompany.cafe_management_app.ui;

import javax.swing.*;
import java.awt.*;

public class InitErrorUI {

    private JFrame frame;
    private JLabel errorLabel;

    JLabel additionalLabel;

    public InitErrorUI(String errorMessage, String additionalText) {
        frame = new JFrame("Initialization error");

        errorLabel = new JLabel(errorMessage);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        additionalLabel = new JLabel(additionalText, SwingConstants.CENTER);
        additionalLabel.setFont(new Font("Monospace", Font.PLAIN, 16));

//        frame.add(errorLabel, BorderLayout.NORTH);
        frame.add(additionalLabel);
        frame.setSize(400, 120);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

    }
}

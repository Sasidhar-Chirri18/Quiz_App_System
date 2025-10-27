package com.example.quizapp.ui;
import javax.swing.*;

import com.example.quizapp.dao.UserDAO;
import com.example.quizapp.model.User;
import com.example.quizapp.util.PasswordUtil;
import java.awt.*;


public class RegisterFrame extends JFrame {

    private JTextField usernameField, fullNameField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public RegisterFrame() {
        setTitle("Quiz App - Register");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblTitle = new JLabel("Create New Account", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitle);

        fullNameField = new JTextField();
        fullNameField.setBorder(BorderFactory.createTitledBorder("Full Name"));
        panel.add(fullNameField);

        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        panel.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        panel.add(passwordField);

        JPanel btnPanel = new JPanel(new FlowLayout());
        registerButton = new JButton("Register");
        backButton = new JButton("Back");
        btnPanel.add(registerButton);
        btnPanel.add(backButton);
        panel.add(btnPanel);

        add(panel);
        setVisible(true);

        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(password);
        user.setFullName(fullName);

        UserDAO dao = new UserDAO();
        if (dao.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
            dispose();
            new LoginFrame();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
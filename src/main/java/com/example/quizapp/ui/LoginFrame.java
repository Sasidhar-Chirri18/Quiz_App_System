package com.example.quizapp.ui;

import javax.swing.*;


import com.example.quizapp.model.User;
import com.example.quizapp.util.PasswordUtil;
import com.example.quizapp.dao.UserDAO;


import java.awt.*;
import java.awt.event.*;


public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginFrame() {
        setTitle("Quiz App - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblTitle = new JLabel("Login to Quiz App", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitle);

        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        panel.add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        panel.add(passwordField);

        JPanel btnPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        btnPanel.add(loginButton);
        btnPanel.add(registerButton);
        panel.add(btnPanel);

        add(panel);
        setVisible(true);

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        UserDAO dao = new UserDAO();
        User user = dao.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose();
             new DashboardFrame(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
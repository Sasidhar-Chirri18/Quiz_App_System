package com.example.quizapp.ui;

import javax.swing.*;

import com.example.quizapp.model.User;

import java.awt.*;

public class DashboardFrame extends JFrame {

    private User currentUser;

    public DashboardFrame(User user) {
        this.currentUser = user;

        setTitle("Quiz Dashboard - " + user.getFullName());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcome = new JLabel("Welcome, " + user.getFullName() + "!", JLabel.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));

        JButton startQuiz = new JButton("Start Quiz");
        startQuiz.setFont(new Font("Arial", Font.PLAIN, 16));
        startQuiz.addActionListener(e -> {
            dispose();
            new QuizFrame(currentUser,1);
        });

        setLayout(new BorderLayout());
        add(welcome, BorderLayout.NORTH);
        add(startQuiz, BorderLayout.CENTER);

        setVisible(true);
    }
}

	
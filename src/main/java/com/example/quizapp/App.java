package com.example.quizapp;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hello Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400,200);
            frame.add(new JLabel("Welcome to Quiz App", SwingConstants.CENTER));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
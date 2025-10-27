package com.example.quizapp.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quiz_app";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("✅ Database connected successfully!");
            }
        } catch (Exception e) {
            System.err.println("❌ DB Connection failed: " + e.getMessage());
        }
        return conn;
    }
}
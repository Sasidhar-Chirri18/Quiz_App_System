package com.example.quizapp.dao;

import com.example.quizapp.model.User;
import com.example.quizapp.util.DBConnection;
import com.example.quizapp.util.PasswordUtil;
import java.sql.*;



public class UserDAO {

    // Register new user
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, password_hash, full_name) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, PasswordUtil.hashPassword(user.getPasswordHash()));
            ps.setString(3, user.getFullName());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Login verification
    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password_hash");
                if (PasswordUtil.checkPassword(password, hashedPassword)) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFullName(rs.getString("full_name"));
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
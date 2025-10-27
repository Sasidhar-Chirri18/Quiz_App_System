package com.example.quizapp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    // Hash password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Check password against stored hash
    public static boolean checkPassword(String password, String storedHash) {
        String hashedInput = hashPassword(password);
        return hashedInput.equals(storedHash);
    }

    // Optional: for quick manual test
    public static void main(String[] args) {
        String password = "test123";
        String hash = hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("Hashed : " + hash);
        System.out.println("Check  : " + checkPassword("test123", hash)); // true
    }
}
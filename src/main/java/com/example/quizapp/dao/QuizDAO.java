package com.example.quizapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.quizapp.model.Quiz;
import com.example.quizapp.util.DBConnection;

public class QuizDAO {
	public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(rs.getInt("id"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDurationMinutes(rs.getInt("duration_minutes"));
                quizzes.add(quiz);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public Quiz getQuizById(int id) {
        String query = "SELECT * FROM quizzes WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Quiz(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("duration_minutes")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}



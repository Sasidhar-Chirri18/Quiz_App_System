package com.example.quizapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.quizapp.model.Question;
import com.example.quizapp.util.DBConnection;

public class QuestionDAO {
	public List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY RAND()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question q = new Question(
                    rs.getInt("id"),
                    rs.getInt("quiz_id"),
                    rs.getString("question_text"),
                    rs.getString("choice_a"),
                    rs.getString("choice_b"),
                    rs.getString("choice_c"),
                    rs.getString("choice_d"),
                    rs.getString("correct_choice").charAt(0),
                    rs.getInt("marks")
                );
                questions.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
}



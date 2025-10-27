package com.example.quizapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.quizapp.model.Result;
import com.example.quizapp.util.DBConnection;
import com.example.quizapp.model.Question;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Map;


public class ResultDAO {

    /**
     * Save a user's quiz attempt. 
     * @param userId user id
     * @param quizId quiz id
     * @param selectedAnswers map questionId -> Character ('A'..'D'), null for unanswered
     * @param startedAt LocalDateTime when quiz started
     * @param finishedAt LocalDateTime when quiz finished
     * @return generated result id (or -1 on failure)
     */
    public static int saveResultWithDetails(int userId, int quizId, Map<Integer, Character> selectedAnswers,
                                            LocalDateTime startedAt, LocalDateTime finishedAt) {
        Connection conn = null;
        int resultId = -1;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            int score = 0;
            int maxScore = 0;
            String qSql = "SELECT id, correct_choice, marks FROM questions WHERE id = ?";
            try (PreparedStatement qPs = conn.prepareStatement(qSql)) {
                for (Map.Entry<Integer, Character> entry : selectedAnswers.entrySet()) {
                    int qid = entry.getKey();
                    Character sel = entry.getValue(); 
                    qPs.setInt(1, qid);
                    try (ResultSet rs = qPs.executeQuery()) {
                        if (rs.next()) {
                            char correct = rs.getString("correct_choice").charAt(0);
                            int marks = rs.getInt("marks");
                            maxScore += marks;
                            if (sel != null && sel == correct) score += marks;
                        }
                    }
                }
            }

            // insert into results
            String insertResult = "INSERT INTO results (user_id, quiz_id, score, max_score, started_at, finished_at) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement p = conn.prepareStatement(insertResult, Statement.RETURN_GENERATED_KEYS)) {
                p.setInt(1, userId);
                p.setInt(2, quizId);
                p.setInt(3, score);
                p.setInt(4, maxScore);
                p.setTimestamp(5, Timestamp.valueOf(startedAt));
                p.setTimestamp(6, Timestamp.valueOf(finishedAt));
                p.executeUpdate();
                try (ResultSet gk = p.getGeneratedKeys()) {
                    if (gk.next()) resultId = gk.getInt(1);
                }
            }

            // insert result_details
            String insertDetail = "INSERT INTO result_details (result_id, question_id, selected_choice, is_correct, marks_obtained) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement dPs = conn.prepareStatement(insertDetail)) {
                for (Map.Entry<Integer, Character> entry : selectedAnswers.entrySet()) {
                    int qid = entry.getKey();
                    Character sel = entry.getValue();
                    String fetch = "SELECT correct_choice, marks FROM questions WHERE id = ?";
                    try (PreparedStatement f = conn.prepareStatement(fetch)) {
                        f.setInt(1, qid);
                        try (ResultSet rs = f.executeQuery()) {
                            if (rs.next()) {
                                char correct = rs.getString("correct_choice").charAt(0);
                                int marks = rs.getInt("marks");
                                boolean isCorrect = (sel != null && sel == correct);
                                int obtained = isCorrect ? marks : 0;

                                dPs.setInt(1, resultId);
                                dPs.setInt(2, qid);
                                if (sel == null) dPs.setNull(3, Types.CHAR);
                                else dPs.setString(3, String.valueOf(sel));
                                dPs.setBoolean(4, isCorrect);
                                dPs.setInt(5, obtained);
                                dPs.addBatch();
                            }
                        }
                    }
                }
                dPs.executeBatch();
            }

            conn.commit();
            return resultId;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ignored) {}
            return -1;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }
}


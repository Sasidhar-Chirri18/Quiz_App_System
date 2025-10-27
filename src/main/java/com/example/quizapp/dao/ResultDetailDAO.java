package com.example.quizapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.quizapp.model.ResultDetail;
import com.example.quizapp.util.DBConnection;


public class ResultDetailDAO {
	 public void saveResultDetail(ResultDetail detail) {
	        String query = "INSERT INTO result_details (result_id, question_id, selected_choice, is_correct, marks_obtained) VALUES (?, ?, ?, ?, ?)";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {

	            ps.setInt(1, detail.getResultId());
	            ps.setInt(2, detail.getQuestionId());
	            ps.setString(3, String.valueOf(detail.getSelectedChoice()));
	            ps.setBoolean(4, detail.isCorrect());
	            ps.setInt(5, detail.getMarksObtained());
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public List<ResultDetail> getDetailsByResultId(int resultId) {
	        List<ResultDetail> details = new ArrayList<>();
	        String query = "SELECT * FROM result_details WHERE result_id = ?";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {

	            ps.setInt(1, resultId);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                ResultDetail d = new ResultDetail(
	                    rs.getInt("id"),
	                    rs.getInt("result_id"),
	                    rs.getInt("question_id"),
	                    rs.getString("selected_choice").charAt(0),
	                    rs.getBoolean("is_correct"),
	                    rs.getInt("marks_obtained")
	                );
	                details.add(d);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return details;
	    }
	}



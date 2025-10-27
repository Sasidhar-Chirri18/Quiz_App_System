package com.example.quizapp.ui;
import javax.swing.*;

import com.example.quizapp.model.User;
import com.example.quizapp.util.DBConnection;

import java.awt.*;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ResultFrame extends JFrame {
	// Construct with a result id and display details from DB
    public ResultFrame(int resultId) {
        setTitle("Quiz Result - #" + resultId);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI(resultId);
        setVisible(true);
    }

    private void initUI(int resultId) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // top summary (score / max)
        String summarySql = "SELECT r.score, r.max_score, qz.title, r.started_at, r.finished_at " +
                "FROM results r JOIN quizzes qz ON r.quiz_id = qz.id WHERE r.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(summarySql)) {

            ps.setInt(1, resultId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int score = rs.getInt("score");
                    int max = rs.getInt("max_score");
                    String title = rs.getString("title");
                    Timestamp sAt = rs.getTimestamp("started_at");
                    Timestamp fAt = rs.getTimestamp("finished_at");
                    JLabel hdr = new JLabel("<html><b>Quiz:</b> " + title +
                            " &nbsp;&nbsp; <b>Score:</b> " + score + " / " + max +
                            " &nbsp;&nbsp; <b>Started:</b> " + sAt +
                            " &nbsp;&nbsp; <b>Finished:</b> " + fAt + "</html>");
                    panel.add(hdr, BorderLayout.NORTH);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // table with per-question details
        String[] cols = {"Q#", "Question", "Your Answer", "Correct Answer", "Marks Obtained"};
        DefaultTableModel tm = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tm);
        table.setRowHeight(40);
        JScrollPane sp = new JScrollPane(table);
        panel.add(sp, BorderLayout.CENTER);

        String detailsSql = "SELECT rd.question_id, q.question_text, rd.selected_choice, q.correct_choice, rd.marks_obtained " +
                "FROM result_details rd JOIN questions q ON rd.question_id = q.id WHERE rd.result_id = ? ORDER BY rd.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(detailsSql)) {

            ps.setInt(1, resultId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int qid = rs.getInt("question_id");
                    String qtext = rs.getString("question_text");
                    String sel = rs.getString("selected_choice");
                    String corr = rs.getString("correct_choice");
                    int marks = rs.getInt("marks_obtained");
                    if (sel == null) sel = "-";
                    Object[] row = {qid, "<html><div style='width:400px'>" + qtext + "</div></html>", sel, corr, marks};
                    tm.addRow(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // export button
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> dispose());
        bottom.add(btnClose);
        panel.add(bottom, BorderLayout.SOUTH);

        getContentPane().add(panel);
    }
}


    
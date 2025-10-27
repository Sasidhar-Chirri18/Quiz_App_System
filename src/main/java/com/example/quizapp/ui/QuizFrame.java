package com.example.quizapp.ui;
import javax.swing.*;
import javax.swing.Timer;

import com.example.quizapp.dao.QuestionDAO;
import com.example.quizapp.dao.ResultDAO;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.User;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;


public class QuizFrame extends JFrame {
	private User user;
    private int quizId;
    private final List<Question> questions;
    private final Map<Integer, Character> selectedAnswers = new LinkedHashMap<>();
    private int currentIndex = 0;

    private final JLabel lblQuestion = new JLabel();
    private final JRadioButton rA = new JRadioButton();
    private final JRadioButton rB = new JRadioButton();
    private final JRadioButton rC = new JRadioButton();
    private final JRadioButton rD = new JRadioButton();
    private final ButtonGroup group = new ButtonGroup();
    private final JLabel timerLabel = new JLabel("Time left: --:--");
    private Timer countdownTimer;
    private int secondsLeft;
    private LocalDateTime startedAt;

    public QuizFrame(User user, int quizId) {
        this.user = user;
        this.quizId = quizId;

        // load questions
        QuestionDAO qDao = new QuestionDAO();
        this.questions = qDao.getQuestionsByQuizId(quizId);

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions found for this quiz.", "No Questions", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // default duration: fetch quiz duration or fallback to 5 minutes
        int durationMinutes = 5; 
        // initialize timer seconds
        this.secondsLeft = durationMinutes * 60;

        setTitle("Quiz - " + user.getUsername());
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();

        startedAt = LocalDateTime.now();

        // Swing timer - ticks every second
        countdownTimer = new Timer(1000, (ActionEvent e) -> {
            secondsLeft--;
            int m = secondsLeft / 60;
            int s = secondsLeft % 60;
            timerLabel.setText(String.format("Time left: %02d:%02d", m, s));
            if (secondsLeft <= 0) {
                countdownTimer.stop();
                JOptionPane.showMessageDialog(this, "Time's up! Auto-submitting your quiz.");
                submitQuiz();
            }
        });
        countdownTimer.start();

        loadQuestion(0);
        setVisible(true);
    }

    private void initUI() {
        lblQuestion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JPanel qPanel = new JPanel(new BorderLayout(10, 10));
        qPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        qPanel.add(lblQuestion, BorderLayout.NORTH);

        group.add(rA); group.add(rB); group.add(rC); group.add(rD);
        JPanel options = new JPanel(new GridLayout(4, 1, 5, 5));
        options.add(rA); options.add(rB); options.add(rC); options.add(rD);
        qPanel.add(options, BorderLayout.CENTER);

        JButton prev = new JButton("Previous");
        JButton next = new JButton("Next");
        JButton submit = new JButton("Submit");

        prev.addActionListener(e -> {
            saveSelection();
            if (currentIndex > 0) loadQuestion(currentIndex - 1);
        });

        next.addActionListener(e -> {
            saveSelection();
            if (currentIndex < questions.size() - 1) loadQuestion(currentIndex + 1);
            else {
                // last question -> offer submit
                int confirmed = JOptionPane.showConfirmDialog(this, "This is the last question. Submit now?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) submitQuiz();
            }
        });

        submit.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Submit quiz now?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) submitQuiz();
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.add(timerLabel);
        bottom.add(prev); bottom.add(next); bottom.add(submit);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(qPanel, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }

    private void loadQuestion(int index) {
        currentIndex = index;
        Question q = questions.get(index);
        lblQuestion.setText(String.format("%d. %s", index + 1, q.getQuestionText()));
        rA.setText("A. " + q.getChoiceA());
        rB.setText("B. " + q.getChoiceB());
        rC.setText("C. " + q.getChoiceC());
        rD.setText("D. " + q.getChoiceD());
        group.clearSelection();

        // restore previous selection if exists
        Character sel = selectedAnswers.get(q.getId());
        if (sel != null) {
            switch (sel) {
                case 'A': rA.setSelected(true); break;
                case 'B': rB.setSelected(true); break;
                case 'C': rC.setSelected(true); break;
                case 'D': rD.setSelected(true); break;
            }
        }
    }

    private void saveSelection() {
        Question q = questions.get(currentIndex);
        Character sel = null;
        if (rA.isSelected()) sel = 'A';
        else if (rB.isSelected()) sel = 'B';
        else if (rC.isSelected()) sel = 'C';
        else if (rD.isSelected()) sel = 'D';
        selectedAnswers.put(q.getId(), sel);
    }

    private void submitQuiz() {
        // stop timer and save last selection
        if (countdownTimer.isRunning()) countdownTimer.stop();
        saveSelection();
        LocalDateTime finishedAt = LocalDateTime.now();

        // ensure every question has an entry
        for (Question q : questions) {
            selectedAnswers.putIfAbsent(q.getId(), null);
        }

        // save to DB
        int resultId = ResultDAO.saveResultWithDetails(user.getId(), quizId, selectedAnswers, startedAt, finishedAt);
        if (resultId > 0) {
            JOptionPane.showMessageDialog(this, "Quiz submitted. Your result id: " + resultId);
            dispose();
            // open ResultFrame which shows details
            SwingUtilities.invokeLater(() -> new ResultFrame(resultId));
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save result. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


   
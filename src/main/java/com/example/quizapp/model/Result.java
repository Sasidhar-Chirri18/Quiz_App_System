package com.example.quizapp.model;

import java.sql.Timestamp;

public class Result {
    private int id;
    private int userId;
    private int quizId;
    private int score;
    private int maxScore;
    private Timestamp startedAt;
    private Timestamp finishedAt;

    public Result() {}

    public Result(int id, int userId, int quizId, int score, int maxScore, Timestamp startedAt, Timestamp finishedAt) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.maxScore = maxScore;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    public Timestamp getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return "Result [id=" + id + ", userId=" + userId + ", quizId=" + quizId +
               ", score=" + score + ", maxScore=" + maxScore + "]";
    }
}
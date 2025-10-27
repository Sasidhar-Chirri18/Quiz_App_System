package com.example.quizapp.model;

public class ResultDetail {
	private int id;
    private int resultId;
    private int questionId;
    private char selectedChoice;
    private boolean isCorrect;
    private int marksObtained;

    public ResultDetail() {}

    public ResultDetail(int id, int resultId, int questionId, char selectedChoice,
                        boolean isCorrect, int marksObtained) {
        this.id = id;
        this.resultId = resultId;
        this.questionId = questionId;
        this.selectedChoice = selectedChoice;
        this.isCorrect = isCorrect;
        this.marksObtained = marksObtained;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public char getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(char selectedChoice) {
        this.selectedChoice = selectedChoice;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }

    @Override
    public String toString() {
        return "ResultDetail [id=" + id + ", resultId=" + resultId + ", questionId=" + questionId +
               ", selectedChoice=" + selectedChoice + ", isCorrect=" + isCorrect +
               ", marksObtained=" + marksObtained + "]";
    }
}




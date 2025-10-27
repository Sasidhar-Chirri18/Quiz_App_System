package com.example.quizapp.model;

public class Question {
	private int id;
    private int quizId;
    private String questionText;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private char correctChoice;
    private int marks;

    public Question() {}

    public Question(int id, int quizId, String questionText, String choiceA, String choiceB,
                    String choiceC, String choiceD, char correctChoice, int marks) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.correctChoice = correctChoice;
        this.marks = marks;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public char getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(char correctChoice) {
        this.correctChoice = correctChoice;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", quizId=" + quizId + ", questionText=" + questionText + "]";
    }
}



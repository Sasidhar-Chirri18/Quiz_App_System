package com.example.quizapp.model;

public class Quiz {
	private int id;
    private String title;
    private int durationMinutes;

    public Quiz() {}

    public Quiz(int id, String title, int durationMinutes) {
        this.id = id;
        this.title = title;
        this.durationMinutes = durationMinutes;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return "Quiz [id=" + id + ", title=" + title + ", durationMinutes=" + durationMinutes + "]";
    }
}



package com.example.taskmanager_app;

public class TaskModel {
    private String title;
    private String description;
    private String date;
    private String time;
    private boolean isComplete;

    public TaskModel(String title, String description, String date, String time, boolean isComplete) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.isComplete = isComplete;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean isComplete() {
        return isComplete;
    }
}

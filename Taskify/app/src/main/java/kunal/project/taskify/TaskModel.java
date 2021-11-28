package kunal.project.taskify;

import java.io.Serializable;

public class TaskModel implements Serializable {
    private String taskId;
    private String title;
    private String description;
    private String date;
    private boolean isComplete;

    public TaskModel() {

    }

    public TaskModel(String taskId, String title, String description, String date, boolean isComplete) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isComplete = isComplete;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getTitle() {
        return title;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public boolean getComplete() {
        return isComplete;
    }
}

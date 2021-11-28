package kunal.project.taskify;

public class TaskModel {
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

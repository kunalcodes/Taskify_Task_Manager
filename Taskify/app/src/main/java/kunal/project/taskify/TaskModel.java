package kunal.project.taskify;

public class TaskModel {
    private String title;
    private String description;
    private String date;
    private boolean isComplete;

    public TaskModel(String title, String description, String date, boolean isComplete) {
        this.title = title;
        this.description = description;
        this.date = date;
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

    public boolean getComplete() {
        return isComplete;
    }
}

package bluebird.tasks;
public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTaskType() {
        return this.getClass().toString().toLowerCase();
    }

    public void markDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return isDone ? "\u001B[32m[X]\u001B[0m" : "[ ]";
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    public String toWritable() {
        return "";
    }
}

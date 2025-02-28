package bluebird.tasks;

/**
 * Represents an event that the user needs to complete.
 */
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

    /**
     * Checks task completion and returns a visual indicator for displaying 
     * back to the user.
     * 
     * @return a green checked-box if task complete, an monotone empty box 
     * otherwise.
     */
    public String getStatusIcon() {
        return isDone ? "\u001B[32m[X]\u001B[0m" : "[ ]";
    }

    /**
     * Gets a printable representation of the Task alongside its completion 
     * indicator.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Formats the task into a save-friendly format for writing into a file.
     */
    public String toWritable() {
        return "";
    }
}

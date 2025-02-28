package bluebird.tasks;

/**
 * Represents a Task that the user needs to complete.
 */
public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public String toString() {
        return "[T] " + super.toString();
    }
    
    public String toWritable() {
        return "T | " + (isDone ? 1 : 0) + " | " + description + "\n";
    }
}

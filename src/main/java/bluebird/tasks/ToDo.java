package bluebird.tasks;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public String toString() {
        return "[T] " + super.toString();
    }

    public String undoCommand() {
        return "t " + description;
    }
    
    public String toWritable() {
        return "T | " + (isDone ? 1 : 0) + " | " + description + "\n";
    }
}

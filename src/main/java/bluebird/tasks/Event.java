package bluebird.tasks;

public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
        setFrom(from);
        setTo(to);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String toString() {
        return "[E] " + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    public String undoCommand() {
        return "e " + description + " /f " + from + " /to " + to;
    }
    
    public String toWritable() {
        return "E | " + (isDone ? 1 : 0) + " | " + description + " | " + from + " | " + to + "\n";
    }
}

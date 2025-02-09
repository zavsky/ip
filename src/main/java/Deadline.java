public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        setBy(by);
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

public class Deadline extends Task{
    // Date for the deadline
    private String by;

    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description, TaskType.DEADLINE);
        this.by = by;
        this.isDone = isDone;
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

public class Event extends Task{
    // Dates for the duration
    private String day;
    private String from;
    private String to;

    public Event(String description, String day, String from, String to) {
        super(description, TaskType.EVENT);
        this.day = day;
        this.from = from;
        this.to = to;
    }

    public Event(String description, String day, String from, String to, boolean isDone) {
        super(description, TaskType.EVENT);
        this.day = day;
        this.from = from;
        this.to = to;
        this.isDone = isDone;
    }

    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + day + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + day + " from: " + from + " to: " + to + ")";
    }
}

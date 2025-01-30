package baymax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    // Date for the deadline
    protected LocalDateTime deadline;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mma");
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Deadline(String description, String deadline) {
        super(description, TaskType.DEADLINE);
        this.deadline = LocalDateTime.parse(deadline, INPUT_FORMAT);
    }

    public Deadline(String description, String deadline, boolean isDone) {
        super(description, TaskType.DEADLINE);
        this.deadline = LocalDateTime.parse(deadline, INPUT_FORMAT);
        this.isDone = isDone;
    }

    public static Deadline fromFileFormat(String description, String dateTime) {
        return new Deadline(description, dateTime);
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + deadline.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(OUTPUT_FORMAT) + ")";
    }
}

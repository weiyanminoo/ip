package baymax.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task in the Baymax application.
 * A Deadline task has a description and a specific due date and time.
 */
public class Deadline extends Task{
    // Date for the deadline
    protected LocalDateTime deadline;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mma");
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Constructs a Deadline task with the given description and deadline.
     *
     * @param description The description of the Deadline task.
     * @param deadline The due date and time of the task in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String description, String deadline) {
        super(description, TaskType.DEADLINE);
        this.deadline = LocalDateTime.parse(deadline, INPUT_FORMAT);
    }

    /**
     * Constructs a Deadline task with the given description, deadline, and completion status.
     * This constructor is used when loading tasks from storage.
     *
     * @param description The description of the Deadline task.
     * @param deadline The due date and time in "yyyy-MM-dd HHmm" format.
     * @param isDone Whether the task is marked as done.
     */
    public Deadline(String description, String deadline, boolean isDone) {
        super(description, TaskType.DEADLINE);
        this.deadline = LocalDateTime.parse(deadline, INPUT_FORMAT);
        this.isDone = isDone;
    }

    /**
     * Creates a Deadline task from a file format representation.
     *
     * @param description The description of the task.
     * @param dateTime The due date and time in "yyyy-MM-dd HHmm" format.
     * @return A new Deadline task instance.
     */
    public static Deadline fromFileFormat(String description, String dateTime) {
        return new Deadline(description, dateTime);
    }

    /**
     * Converts the Deadline task into a file-friendly format for storage.
     *
     * @return A formatted string representing the Deadline task for file storage.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + deadline.format(INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return The formatted Deadline task string.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline.format(OUTPUT_FORMAT) + ")";
    }
}

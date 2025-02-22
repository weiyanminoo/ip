package baymax.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task in the Baymax application.
 * An Event task has a description, a specific date, and a time range.
 */
public class Event extends Task{

    private LocalDate date;
    private LocalTime from;
    private LocalTime to;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a");

    /**
     * Constructs an Event task with the given description, date, start time, and end time.
     *
     * @param description The description of the Event task.
     * @param date The date of the event in "yyyy-MM-dd" format.
     * @param from The start time in "HHmm" format.
     * @param to The end time in "HHmm" format.
     */
    public Event(String description, String date, String from, String to) {
        super(description, TaskType.EVENT);
        assert date != null && !date.trim().isEmpty() : "Event date cannot be null or empty";
        assert from != null && !from.trim().isEmpty() : "Event start time cannot be null or empty";
        assert to != null && !to.trim().isEmpty() : "Event end time cannot be null or empty";

        this.date = LocalDate.parse(date);
        this.from = LocalTime.parse(from);
        this.to = LocalTime.parse(to);
        assert this.to.isAfter(this.from) : "Event end time must be after start time";
    }

    /**
     * Constructs an Event task with the given description, date, time range, and completion status.
     * This constructor is used when loading tasks from storage.
     *
     * @param description The description of the Event task.
     * @param date The date of the event in "yyyy-MM-dd" format.
     * @param from The start time in "HHmm" format.
     * @param to The end time in "HHmm" format.
     * @param isDone Whether the task is marked as done.
     */
    public Event(String description, String date, String from, String to, boolean isDone) {
        super(description, TaskType.EVENT);
        this.date = LocalDate.parse(date);
        this.from = LocalTime.parse(from);
        this.to = LocalTime.parse(to);
        this.isDone = isDone;
    }

    /**
     * Converts the Event task into a file-friendly format for storage.
     *
     * @return A formatted string representing the Event task for file storage.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + date + " | " + from + " | " + to;
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return The formatted Event task string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (on: " + date.format(DATE_FORMAT)
                + " from: " + from.format(TIME_FORMAT) + " to: " + to.format(TIME_FORMAT) + ")";
    }
}
